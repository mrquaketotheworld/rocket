(ns common
  (:require [babashka.fs :as fs]
            [clojure.string :as str]))

(def root
  (or (System/getenv "ROCKET_ROOT")
      (str (fs/cwd))))

(defn rel [& parts]
  (str (apply fs/path root parts)))

(defn md-files
  "Все markdown-файлы в каталогах слоёв знаний и шаблонов."
  []
  (->> ["01-standards" "02-foundation" "04-templates"]
       (mapcat (fn [d]
                 (let [p (fs/path root d)]
                   (when (fs/exists? p)
                     (fs/glob p "**.md")))))
       (map str)
       sort))

(defn parse-frontmatter
  "Возвращает map полей frontmatter (значения как строки) или nil, если блока нет."
  [content]
  (when (str/starts-with? content "---")
    (let [lines (str/split-lines content)
          end   (->> (map-indexed vector (rest lines))
                     (filter (fn [[_ l]] (= "---" (str/trim l))))
                     ffirst)]
      (when end
        (->> (take end (rest lines))
             (keep (fn [l]
                     (when-let [[_ k v] (re-matches #"^([a-zA-Z_][\w]*):\s*(.*)$" l)]
                       [(keyword k) (str/trim v)])))
             (into {}))))))

(defn read-edn [path]
  (when (fs/exists? path)
    (-> path str slurp clojure.edn/read-string)))
