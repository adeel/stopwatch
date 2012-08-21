(ns stopwatch.client
  (:require [cljs.nodejs :as node]
            [stopwatch.api :as api]
            [stopwatch.logger :as logger])
  (:use [stopwatch.util :only (format-minutes)]))

(def Table (node/require "cli-table"))

(defn help []
  (println "You need help."))

(defn summary []
  (let [rs      (logger/read)
        summary (api/summarize rs)
        table   (Table. (js-obj "style" (js-obj "compact" true)
                                "chars" (js-obj
                                          "top"          " "
                                          "top-mid"      " "
                                          "top-left"     ""
                                          "top-right"    " "
                                          "bottom"       " "
                                          "bottom-mid"   " "
                                          "bottom-left"  ""
                                          "bottom-right" " "
                                          "left"         ""
                                          "left-mid"     ""
                                          "mid"          " "
                                          "mid-mid"      " "
                                          "right"        " "
                                          "right-mid"    " ")))]
    (doseq [[project minutes] summary]
      (.push table (array project (format-minutes minutes))))
    (println (.toString table))))

(defn start [project]
  (logger/write (api/start-project project (logger/read)))
  (println "Starting work on project: " project))
