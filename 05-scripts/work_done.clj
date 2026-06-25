(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns work-done
  (:require [common]
            [clojure.string :as str]
            [babashka.fs :as fs]))

(defn parse-args [args]
  (loop [m {} xs args]
    (if (empty? xs)
      m
      (case (first xs)
        "--task" (recur (assoc m :task (second xs)) (nnext xs))
        (recur m (next xs))))))

(def locks-dir (common/rel "03-execution" "05-locks"))

(defn task-file [task-id]
  (common/rel "03-execution" "02-tasks" (str task-id ".md")))

(defn update-task-file-status! [task-id status]
  (let [path (task-file task-id)]
    (when (fs/exists? path)
      (let [content (slurp path)
            updated (-> content
                        (str/replace-first #"(?m)^status:\s*\S+" (str "status: " status))
                        (common/update-frontmatter-field :last_updated (common/today)))]
        (spit path updated)))))

(defn -main []
  (let [m       (parse-args *command-line-args*)
        task-id (:task m)]
    (when-not task-id
      (println "[work-done] ERROR: укажите --task TASK-XXX")
      (System/exit 1))
    (let [lock-file (str (fs/path locks-dir (str task-id ".edn")))]
      (when-not (fs/exists? lock-file)
        (println (str "[work-done] ERROR: нет активного claim для " task-id
                      ". Сначала bb work-claim --task " task-id))
        (System/exit 1))
      ;; обновить статус задачи в реестре и файле задачи
      (let [reg-path (common/rel "03-execution" "02-tasks" "task-registry.edn")
            registry (common/read-edn reg-path)
            tasks    (mapv (fn [t]
                             (if (= (:id t) task-id)
                               (assoc t :status "done")
                               t))
                           (:tasks registry))]
        (spit reg-path (with-out-str (clojure.pprint/pprint (assoc registry
                                                                    :last_updated (common/today)
                                                                    :tasks tasks)))))
      (update-task-file-status! task-id "done")
      (fs/delete lock-file)
      (println (str "[work-done] OK: " task-id " закрыта, claim снят, task file и registry переведены в done."))
      (println "  Убедитесь, что current-state.edn и current-session.md уже обновлены.")
      (println "  Если задача меняла репозиторий, сделайте локальный commit в dev.")
      (println "  Push в remote выполняется только по явной команде человека."))))

(-main)
