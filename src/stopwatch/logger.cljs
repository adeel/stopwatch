(ns stopwatch.logger
  (:use [clojure.string :only (split-lines join)]
        [stopwatch.util :only (get-log-path get-datetime-format)])
  (:require [cljs.nodejs :as node]))

(def fs (node/require "fs"))
(def moment (node/require "moment"))

;; reading

(defn- parse-datetime [date]
  (.toDate (moment date (get-datetime-format))))

(defn- parse-record [r]
  (let [[_ project start end] (re-find #"([^\:]+)\:\s*(.+)\s+\-\s*(.+)?" r)
        record                {:project project
                               :start (parse-datetime start)}]
    (if-not end
      record
      (assoc record :end (parse-datetime end)))))

(defn read
  ([]
    (read (get-log-path)))
  ([filename]
    (try
      (vec (map parse-record (split-lines (.readFileSync fs filename "utf-8"))))
      (catch js/Error e []))))

;; writing

(defn- unparse-datetime [datetime]
  (.format (moment datetime) (get-datetime-format)))

(defn- unparse-record [{:keys [project start end]}]
  (str project ": " (unparse-datetime start) " - "
    (if end (unparse-datetime end) "")))

(defn write
  ([records]
    (write records (get-log-path)))
  ([records filename]
    (.writeFileSync fs filename (join "\n" (map unparse-record records)))))
