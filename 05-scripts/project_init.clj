(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns project-init
  (:require [babashka.fs :as fs]
            [clojure.string :as str]))

(defn parse-args [args]
  (loop [m {} xs args]
    (if (empty? xs)
      m
      (case (first xs)
        "--target" (recur (assoc m :target (second xs)) (nnext xs))
        (recur m (next xs))))))

(defn today []
  (str (java.time.LocalDate/now)))

(defn excluded? [p]
  (let [s (str (fs/relativize common/root p))]
    (or (= s ".git")
        (str/starts-with? s ".git/")
        (= s ".rocket-agent-id")
        (str/starts-with? s "03-execution/05-locks/")
        (re-matches #"03-execution/02-tasks/TASK-\d+\.md" s))))

(defn copy-tree! [target]
  (doseq [p (fs/glob common/root "**")]
    (when (and (fs/regular-file? p) (not (excluded? p)))
      (let [rel (fs/relativize common/root p)
            dst (fs/path target rel)]
        (fs/create-dirs (fs/parent dst))
        (fs/copy p dst {:replace-existing true})))))

(defn reset-execution! [target]
  (fs/create-dirs (fs/path target "03-execution" "01-state"))
  (fs/create-dirs (fs/path target "03-execution" "02-tasks"))
  (fs/create-dirs (fs/path target "03-execution" "03-session"))
  (fs/create-dirs (fs/path target "03-execution" "04-logs"))
  (fs/create-dirs (fs/path target "03-execution" "05-locks"))
  (spit (str (fs/path target "03-execution" "02-tasks" "task-registry.edn"))
        (pr-str {:doc_id "TASK-REGISTRY"
                 :doc_type "state"
                 :title "Реестр задач"
                 :authority "operational"
                 :layer "execution"
                 :last_updated (today)
                 :owner "execution"
                 :next_id 1
                 :tasks []}))
  (spit (str (fs/path target "03-execution" "01-state" "current-state.edn"))
        (pr-str {:doc_id "STATE-CURRENT"
                 :doc_type "state"
                 :title "Текущее состояние проекта"
                 :authority "operational"
                 :layer "execution"
                 :status "active"
                 :last_updated (today)
                 :owner "execution"
                 :framework_version (str/trim (slurp (str (fs/path common/root "VERSION"))))
                 :active_tasks []
                 :blockers []
                 :health "green"
                 :canonical_refs ["GOV-EXECUTION-MODEL" "GOV-GIT-WORKFLOW"]
                 :verification_summary "Downstream-проект создан из Rocket. Заполните foundation и создайте первую задачу."}))
  (spit (str (fs/path target "03-execution" "03-session" "current-session.md"))
        (str "---\ndoc_type: session\ndoc_id: SESSION-CURRENT\ntitle: Текущая сессия\nstatus: active\nowner: execution\nlast_updated: " (today) "\nauthority: operational\nlayer: execution\n---\n\n# Текущая сессия\n\n## Текущий фокус\n\nЗаполнить foundation downstream-проекта.\n\n## Выходы\n\n- Проект создан из Rocket.\n\n## Следующие шаги\n\n- Заполнить product/project truth.\n- Прогнать `bb docs-lint` и `bb state-lint`.\n- Создать первую задачу.\n\n## Входы\n\n- GOV-FOUNDATION-CONTRACT\n- PROJ-ENGINEERING\n"))
  (spit (str (fs/path target "03-execution" "05-locks" ".gitkeep")) ""))

(defn -main []
  (let [{:keys [target]} (parse-args *command-line-args*)]
    (when (str/blank? target)
      (println "[project-init] ERROR: укажите --target PATH")
      (System/exit 1))
    (let [target-path (fs/path target)]
      (when (and (fs/exists? target-path) (seq (fs/list-dir target-path)))
        (println (str "[project-init] ERROR: target не пустой: " target))
        (System/exit 1))
      (fs/create-dirs target-path)
      (copy-tree! target-path)
      (reset-execution! target-path)
      (println (str "[project-init] OK: downstream skeleton создан в " target-path))
      (println "  Заполните 02-foundation и выполните bb docs-lint && bb state-lint."))))

(-main)
