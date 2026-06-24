(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns work-claim
  (:require [common]
            [clojure.string :as str]
            [babashka.fs :as fs]))

(defn parse-args [args]
  (loop [m {} xs args]
    (if (empty? xs)
      m
      (case (first xs)
        "--task"  (recur (assoc m :task (second xs)) (nnext xs))
        "--agent" (recur (assoc m :agent (second xs)) (nnext xs))
        (recur m (next xs))))))

(def locks-dir (common/rel "03-execution" "05-locks"))

(defn agent-id [m]
  (or (:agent m)
      (let [f (fs/path common/root ".rocket-agent-id")]
        (if (fs/exists? f)
          (str/trim (slurp (str f)))
          (let [id (str "agent-" (subs (str (random-uuid)) 0 8))]
            (spit (str f) id)
            id)))))

(defn task-entry [registry task-id]
  (->> (:tasks registry) (filter #(= (:id %) task-id)) first))

(defn active-claims []
  (when (fs/exists? locks-dir)
    (->> (fs/glob locks-dir "*.edn")
         (map (comp common/read-edn str))
         (remove nil?))))

(defn overlap? [a b]
  (seq (clojure.set/intersection (set a) (set b))))

(defn task-file [task-id]
  (common/rel "03-execution" "02-tasks" (str task-id ".md")))

(defn update-task-file-status! [task-id status]
  (let [path (task-file task-id)]
    (when (fs/exists? path)
      (let [content (slurp path)
            updated (str/replace-first content #"(?m)^status:\s*\S+" (str "status: " status))]
        (spit path updated)))))

(defn update-registry-status! [registry task-id status]
  (let [tasks (mapv (fn [t]
                      (if (= (:id t) task-id)
                        (assoc t :status status)
                        t))
                    (:tasks registry))]
    (spit (common/rel "03-execution" "02-tasks" "task-registry.edn")
          (with-out-str (clojure.pprint/pprint (assoc registry :tasks tasks))))))

(defn -main []
  (let [m        (parse-args *command-line-args*)
        task-id  (:task m)
        agent    (agent-id m)]
    (when-not task-id
      (println "[work-claim] ERROR: укажите --task TASK-XXX")
      (System/exit 1))
    (let [registry (common/read-edn (common/rel "03-execution" "02-tasks" "task-registry.edn"))
          entry    (task-entry registry task-id)]
      (when-not entry
        (println (str "[work-claim] ERROR: задача " task-id " не найдена в реестре"))
        (System/exit 1))
      (when-not (= "ready" (:status entry))
        (println (str "[work-claim] ERROR: задача " task-id " должна быть ready, текущий статус: " (:status entry)))
        (System/exit 1))
      (let [lock-file (str (fs/path locks-dir (str task-id ".edn")))
            owned     (:owned_modules entry)]
        (when (fs/exists? lock-file)
          (println (str "[work-claim] ERROR: задача " task-id " уже заклеймлена"))
          (System/exit 1))
        ;; проверка пересечения owned_modules с активными claim'ами
        (doseq [c (active-claims)]
          (when (overlap? owned (:owned_modules c))
            (println (str "[work-claim] ERROR: owned_modules пересекаются с активным claim "
                          (:task c) " (agent " (:agent c) ")"))
            (System/exit 1)))
        (fs/create-dirs locks-dir)
        (spit lock-file
              (pr-str {:task task-id
                       :agent agent
                       :owned_modules owned
                       :claimed_at (str (java.time.Instant/now))}))
        (update-registry-status! registry task-id "in_progress")
        (update-task-file-status! task-id "in_progress")
        (println (str "[work-claim] OK: " task-id " заклеймлена агентом " agent " и переведена в in_progress"))
        (when (seq owned)
          (println (str "  owned_modules: " (str/join ", " owned))))
        (println "  single-agent режим: работайте в dev. Для параллельной работы используйте отдельный worktree (см. git-workflow.md).")))))

(-main)
