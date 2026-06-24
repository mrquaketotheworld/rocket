(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns scripts-test
  (:require [babashka.fs :as fs]
            [babashka.process :refer [shell]]
            [clojure.string :as str]))

(defn run! [dir cmd]
  (let [res (shell {:dir (str dir) :continue true :out :string :err :string} cmd)]
    (when-not (zero? (:exit res))
      (println (:out res))
      (println (:err res))
      (throw (ex-info (str "command failed: " cmd) {:cmd cmd :exit (:exit res)})))
    res))

(defn -main []
  (let [tmp (fs/create-temp-dir {:prefix "rocket-scripts-test-"})
        target (fs/path tmp "downstream")]
    (try
      (println (str "scripts-test: temp " tmp))
      (run! common/root (str "bb project-init --target " target))
      (run! target "bb docs-lint")
      (run! target "bb state-lint")
      (run! target "bb commit-lint --message \"TASK-999: smoke commit lint\"")
      (println "OK: scripts smoke tests passed")
      (finally
        (fs/delete-tree tmp)))))

(-main)
