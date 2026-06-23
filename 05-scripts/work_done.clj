(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns work-done
  (:require [common]
            [babashka.fs :as fs]))

(defn parse-args [args]
  (loop [m {} xs args]
    (if (empty? xs)
      m
      (case (first xs)
        "--task" (recur (assoc m :task (second xs)) (nnext xs))
        (recur m (next xs))))))

(def locks-dir (common/rel "03-execution" "05-locks"))

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
      ;; обновить статус задачи в реестре
      (let [reg-path (common/rel "03-execution" "02-tasks" "task-registry.edn")
            registry (common/read-edn reg-path)
            tasks    (mapv (fn [t]
                             (if (= (:id t) task-id)
                               (assoc t :status "done")
                               t))
                           (:tasks registry))]
        (spit reg-path (with-out-str (clojure.pprint/pprint (assoc registry :tasks tasks)))))
      (fs/delete lock-file)
      (println (str "[work-done] OK: " task-id " закрыта, claim снят."))
      (println "  Не забудьте обновить current-state.edn и current-session.md, если состояние изменилось.")
      (println "  Push в remote выполняется только по явной команде человека."))))

(-main)
