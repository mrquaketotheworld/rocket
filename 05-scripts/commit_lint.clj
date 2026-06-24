(ns commit-lint
  (:require [babashka.process :refer [shell]]
            [clojure.string :as str]))

(def pattern #"^TASK-\d{3,}: .+")

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

(defn -main []
  (let [args (parse-args *command-line-args*)
        s (subject args)]
    (if (re-matches pattern s)
      (println (str "commit-lint: OK — " s))
      (do
        (println "commit-lint: ERROR")
        (println (str "  subject: " (pr-str s)))
        (println "  commit message must start with: TASK-XXX: ")
        (println "  example: TASK-007: add commit message lint")
        (println "  forbidden examples: docs: ..., feat: ..., fix: ..., chore: ...")
        (System/exit 1)))))

(-main)
