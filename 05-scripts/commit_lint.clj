(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns commit-lint
  (:require [common]
            [babashka.fs :as fs]
            [babashka.process :refer [shell]]
            [clojure.string :as str]))

(def pattern #"^(TASK-\d{3,}): .+")

(defn parse-args [args]
  (loop [m {} xs args]
    (if (empty? xs)
      m
      (case (first xs)
        "--msg-file" (recur (assoc m :msg-file (second xs)) (nnext xs))
        "--message"  (recur (assoc m :message (second xs)) (nnext xs))
        (recur m (next xs))))))

(defn last-commit-subject []
  (-> (shell {:out :string :err :string} "git log -1 --pretty=%s")
      :out
      str/trim))

(defn first-line [s]
  (-> (or s "") str/split-lines first str/trim))

(defn subject [{:keys [msg-file message]}]
  (cond
    msg-file (first-line (slurp msg-file))
    message  (first-line message)
    :else    (last-commit-subject)))

(defn registry-task [task-id]
  (let [path (common/rel "03-execution" "02-tasks" "task-registry.edn")]
    (when (fs/exists? path)
      (->> (:tasks (common/read-edn path))
           (filter #(= task-id (:id %)))
           first))))

(defn fail! [s & lines]
  (println "commit-lint: ERROR")
  (println (str "  subject: " (pr-str s)))
  (doseq [line lines] (println (str "  " line)))
  (System/exit 1))

(defn -main []
  (let [args (parse-args *command-line-args*)
        s (subject args)]
    (if-let [[_ task-id] (re-matches pattern s)]
      (if (registry-task task-id)
        (println (str "commit-lint: OK — " s))
        (fail! s
               (str "task id not found in task-registry.edn: " task-id)
               "commit message must reference an existing task"))
      (fail! s
             "commit message must start with: TASK-XXX: "
             "example: TASK-008: add verify and todo lint"
             "forbidden examples: docs: ..., feat: ..., fix: ..., chore: ..."))))

(-main)
