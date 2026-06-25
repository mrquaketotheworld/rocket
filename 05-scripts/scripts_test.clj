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
      (let [reg-path (fs/path target "03-execution" "02-tasks" "task-registry.edn")
            task-path (fs/path target "03-execution" "02-tasks" "TASK-001.md")
            original-registry (slurp (str reg-path))
            task-content (str "---\n"
                              "doc_type: task\n"
                              "doc_id: TASK-001\n"
                              "title: Smoke stale registry date\n"
                              "status: ready\n"
                              "owner: execution\n"
                              "last_updated: " (java.time.LocalDate/now) "\n"
                              "authority: operational\n"
                              "layer: execution\n"
                              "canonical_refs: [PROJ-ENGINEERING]\n"
                              "owned_modules: [03-execution]\n"
                              "---\n\n"
                              "# TASK-001: Smoke stale registry date\n\n"
                              "## Цель\n\nSmoke test.\n\n"
                              "## Canonical refs\n\n- PROJ-ENGINEERING\n\n"
                              "## Owned modules\n\n- `03-execution/`\n\n"
                              "## План\n\n1. Smoke.\n\n"
                              "## Верификация\n\n- `bb state-lint`\n\n"
                              "## Definition of Done\n\n- [ ] smoke\n")
            stale-registry (pr-str {:doc_id "TASK-REGISTRY"
                                    :authority "operational"
                                    :last_updated "2000-01-01"
                                    :title "Реестр задач"
                                    :tasks [{:id "TASK-001"
                                             :title "Smoke stale registry date"
                                             :status "ready"
                                             :canonical_refs ["PROJ-ENGINEERING"]
                                             :owned_modules ["03-execution"]}]
                                    :next_id 2
                                    :layer "execution"
                                    :doc_type "state"
                                    :owner "execution"})
            res (do
                  (spit (str task-path) task-content)
                  (spit (str reg-path) stale-registry)
                  (shell {:dir (str target) :continue true :out :string :err :string} "bb state-lint"))]
        (fs/delete-if-exists task-path)
        (spit (str reg-path) original-registry)
        (when (zero? (:exit res))
          (println (:out res))
          (println (:err res))
          (throw (ex-info "expected bb state-lint to fail on stale task-registry last_updated"
                          {:cmd "bb state-lint" :exit (:exit res)}))))
      (run! common/root "bb commit-lint --message \"TASK-008: smoke commit lint\"")
      (println "OK: scripts smoke tests passed")
      (finally
        (fs/delete-tree tmp)))))

(-main)
