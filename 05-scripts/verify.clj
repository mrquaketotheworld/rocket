(ns verify
  (:require [babashka.process :refer [shell]]))

(defn run! [cmd]
  (println (str "verify: " cmd))
  (let [res (shell {:continue true} cmd)]
    (when-not (zero? (:exit res))
      (println (str "verify: FAILED — " cmd))
      (System/exit (:exit res)))))

(defn -main []
  (run! "bb docs-lint")
  (run! "bb state-lint")
  (run! "bb scripts-test")
  (println "verify: OK"))

(-main)
