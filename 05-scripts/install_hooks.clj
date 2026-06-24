(ns install-hooks
  (:require [babashka.process :refer [shell]]))

(defn -main []
  (shell "git config core.hooksPath .githooks")
  (println "install-hooks: OK — git config core.hooksPath .githooks"))

(-main)
