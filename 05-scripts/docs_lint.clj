(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns docs-lint
  (:require [common]
            [clojure.string :as str]
            [babashka.fs :as fs]))

(def required-fields
  [:doc_type :doc_id :title :status :owner :last_updated :authority :layer])

(def valid-authority #{"canonical" "operational"})
(def valid-layer     #{"knowledge" "execution" "template"})
(def valid-status    #{"active" "draft" "superseded" "cancelled" "accepted" "proposed"
                       "ready" "in_progress" "done"})

(defn template? [path]
  (str/includes? (str path) "04-templates"))

;; --- проверка внутренних ссылок ---

(defn root-md-files
  "Markdown-файлы корня (README/AGENTS/CLAUDE) дополнительно проверяются на ссылки."
  []
  (->> ["README.md" "AGENTS.md" "CLAUDE.md"]
       (map #(str (fs/path common/root %)))
       (filter fs/exists?)))

(defn extract-links
  "Возвращает [link-target ...] для markdown-ссылок вида [text](target).
  Внешние (http/mailto) и чисто-якорные (#...) отбрасываются."
  [content]
  (->> (re-seq #"\]\(([^)]+)\)" content)
       (map second)
       (remove #(re-find #"^(https?:|mailto:|#)" %))))

(defn slugify
  "Преобразует заголовок в GitHub-style anchor."
  [heading]
  (-> heading
      str/trim
      str/lower-case
      (str/replace #"[^\p{L}\p{N}\s-]" "")
      (str/replace #"\s+" "-")))

(defn file-anchors
  "Множество доступных якорей (slug заголовков) в markdown-файле."
  [path]
  (when (fs/exists? path)
    (->> (str/split-lines (slurp (str path)))
         (keep (fn [l]
                 (when-let [[_ h] (re-matches #"^#{1,6}\s+(.*)$" l)]
                   (slugify h))))
         set)))

(defn check-links
  "Проверяет относительные ссылки в файле: целевой файл существует и якорь (если есть) валиден."
  [path]
  (let [content (slurp (str path))
        dir     (fs/parent path)]
    (->> (extract-links content)
         (keep (fn [target]
                 (let [[file anchor] (str/split target #"#" 2)
                       file          (if (str/blank? file) (str (fs/file-name path)) file)
                       resolved      (fs/path dir file)]
                   (cond
                     (not (fs/exists? resolved))
                     (str "битая ссылка на файл: " target)

                     (and anchor
                          (str/ends-with? (str resolved) ".md")
                          (not (contains? (file-anchors resolved) anchor)))
                     (str "битый якорь в ссылке: " target))))))))

(defn lint-file [path]
  (let [content (slurp path)
        fm      (common/parse-frontmatter content)
        errs    (atom [])
        add!    #(swap! errs conj %)]
    (if (nil? fm)
      (add! "нет frontmatter-блока")
      (do
        (doseq [f required-fields]
          (when (str/blank? (get fm f))
            (add! (str "отсутствует поле " (name f)))))
        (when-let [a (:authority fm)]
          (when-not (valid-authority a)
            (add! (str "недопустимый authority: " a))))
        (when-let [l (:layer fm)]
          (when-not (valid-layer l)
            (add! (str "недопустимый layer: " l))))
        (when-let [s (:status fm)]
          (when-not (valid-status s)
            (add! (str "недопустимый status: " s))))
        ;; даты последнего обновления формат YYYY-MM-DD (шаблоны исключены)
        (when-let [d (:last_updated fm)]
          (when (and (not (template? path))
                     (not (re-matches #"\d{4}-\d{2}-\d{2}" d)))
            (add! (str "last_updated не в формате YYYY-MM-DD: " d))))))
    ;; битые внутренние ссылки (для всех markdown, включая шаблоны)
    (doseq [e (check-links path)] (add! e))
    {:path path :fm fm :errors @errs}))

(defn lint-root-links
  "Корневые md-файлы: только проверка ссылок, без требований к frontmatter."
  [path]
  {:path path :fm nil :errors (vec (check-links path))})

(defn -main []
  (let [files       (common/md-files)
        results     (concat (map lint-file files)
                            (map lint-root-links (root-md-files)))
        ;; проверка уникальности doc_id (шаблоны с XXXX исключаем)
        ids     (->> results
                     (keep (fn [{:keys [path fm]}]
                             (when (and fm (not (template? path)))
                               [(:doc_id fm) path])))
                     (remove (fn [[id _]] (or (nil? id) (str/includes? (or id "") "XXX")))))
        dup     (->> (group-by first ids)
                     (filter (fn [[_ v]] (> (count v) 1)))
                     (into {}))
        file-errs (filter (comp seq :errors) results)
        total-err (+ (count file-errs) (count dup))]
    (println (str "docs-lint: проверено файлов " (count results)))
    (doseq [{:keys [path errors]} file-errs]
      (println (str "  ✗ " (fs/relativize common/root path)))
      (doseq [e errors] (println (str "      - " e))))
    (doseq [[id paths] dup]
      (println (str "  ✗ дублирующийся doc_id " id ":"))
      (doseq [[_ p] paths] (println (str "      - " (fs/relativize common/root p)))))
    (if (zero? total-err)
      (println "OK: документация консистентна")
      (do (println (str "ОШИБОК: " total-err))
          (System/exit 1)))))

(-main)
