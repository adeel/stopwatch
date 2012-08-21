(ns stopwatch.core
  (:require [stopwatch.client :as client]))

(defn dispatch [command & args]
  (case command
    "summary"     client/summary
    "start"       client/start
                  client/help))

(defn -main [& args]
  (let [handler (apply dispatch args)]
    (apply handler (rest args))))

(set! *main-cli-fn* -main)