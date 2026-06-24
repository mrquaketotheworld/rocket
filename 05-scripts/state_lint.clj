(load-file (str (babashka.fs/path (babashka.fs/parent *file*) "common.clj")))

(ns state-lint
  (:require [common]
            [babashka.fs :as fs]
            [clojure.string :as str]))

(def valid-task-status #{"ready" "in_progress" "done" "superseded" "cancelled"})
(def valid-health #{"green" "yellow" "red"})

(defn err [errors path msg]
  (swap! errors conj {:path path :msg msg}))

(defn parse-status [content]
  (second (re-find #"(?m)^status:\s*([^\s]+)" content)))

(defn has-section? [content title]
  (re-find (re-pattern (str "(?m)^##\\s+" (java.util.regex.Pattern/quote title) "\\s*$")) content))

(defn checked? [content]
  (boolean (re-find #"(?m)^- \[x\]" content)))

(defn lint-task! [errors registry-task]
  (let [id (:id registry-task)
        path (common/rel "03-execution" "02-tasks" (str id ".md"))]
    (when-not (re-matches #"TASK-\d{3,}" (or id ""))
      (err errors "task-registry.edn" (str "некорректный task id: " id)))
    (when-not (contains? valid-task-status (:status registry-task))
      (err errors "task-registry.edn" (str id ": недопустимый status " (:status registry-task))))
    (when-not (seq (:canonical_refs registry-task))
      (err errors "task-registry.edn" (str id ": пустой canonical_refs")))
    (when-not (seq (:owned_modules registry-task))
      (err errors "task-registry.edn" (str id ": пустой owned_modules")))
    (if-not (fs/exists? path)
      (err errors "task-registry.edn" (str id ": нет файла задачи " path))
      (let [content (slurp path)
            file-status (parse-status content)]
        (when-not (= (:status registry-task) file-status)
          (err errors (str "03-execution/02-tasks/" id ".md")
               (str "status в файле (" file-status ") не совпадает с registry (" (:status registry-task) ")")))
        (doseq [section ["Цель" "Canonical refs" "Owned modules" "План" "Верификация" "Definition of Done"]]
          (when-not (has-section? content section)
            (err errors (str "03-execution/02-tasks/" id ".md") (str "нет секции ## " section))))
        (when (= "done" (:status registry-task))
          (when-not (has-section? content "Evidence")
            (err errors (str "03-execution/02-tasks/" id ".md") "done task без секции ## Evidence"))
          (when-not (checked? content)
            (err errors (str "03-execution/02-tasks/" id ".md") "done task без отмеченных пунктов DoD")))))))

(defn -main []
  (let [errors (atom [])
        reg-path (common/rel "03-execution" "02-tasks" "task-registry.edn")
        state-path (common/rel "03-execution" "01-state" "current-state.edn")
        session-path (common/rel "03-execution" "03-session" "current-session.md")
        registry (common/read-edn reg-path)
        state (common/read-edn state-path)
        tasks (:tasks registry)]
    (when-not (= "TASK-REGISTRY" (:doc_id registry))
      (err errors "task-registry.edn" ":doc_id должен быть TASK-REGISTRY"))
    (when-not (integer? (:next_id registry))
      (err errors "task-registry.edn" ":next_id должен быть integer"))
    (let [ids (map :id tasks)]
      (doseq [[id group] (group-by identity ids)]
        (when (> (count group) 1)
          (err errors "task-registry.edn" (str "дублирующийся task id " id))))
      (let [nums (->> ids
                      (keep #(second (re-find #"TASK-(\d+)" (or % ""))))
                      (map #(Integer/parseInt %))
                      seq)]
        (when nums
          (let [max-n (apply max nums)]
            (when (<= (:next_id registry) max-n)
              (err errors "task-registry.edn" (str ":next_id " (:next_id registry) " не больше максимального task id " max-n)))))))
    (doseq [t tasks] (lint-task! errors t))
    (when-not (= "STATE-CURRENT" (:doc_id state))
      (err errors "current-state.edn" ":doc_id должен быть STATE-CURRENT"))
    (when-not (contains? valid-health (:health state))
      (err errors "current-state.edn" (str "недопустимый :health " (:health state))))
    (let [in-progress (set (map :id (filter #(= "in_progress" (:status %)) tasks)))
          active (set (:active_tasks state))]
      (when-not (= in-progress active)
        (err errors "current-state.edn"
             (str ":active_tasks " active " не совпадает с in_progress задачами " in-progress))))
    (when-not (fs/exists? session-path)
      (err errors "current-session.md" "файл текущей сессии отсутствует"))
    (println (str "state-lint: проверено задач " (count tasks)))
    (if (seq @errors)
      (do
        (doseq [{:keys [path msg]} @errors]
          (println (str "  ✗ " path))
          (println (str "      - " msg)))
        (println (str "ОШИБОК: " (count @errors)))
        (System/exit 1))
      (println "OK: операционное состояние консистентно"))))

(-main)
