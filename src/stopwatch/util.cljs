(ns stopwatch.util
  (:require [cljs.nodejs :as node]))

(def fs (node/require "fs"))
(def path (node/require "path"))

(defn get-log-path []
  (let [p (or (.-STOPWATCH_LOG_PATH (.-env node/process))
              (.join path (.-HOME (.-env node/process)) ".stopwatch"))]
    (if-not (.existsSync path p)
      (.writeSync fs p "" 0))
    p))

(defn get-datetime-format []
  (or (.-STOPWATCH_DATETIME_FORMAT (.-env node/process))
      "HH:mm on DD.MM.YYYY"))

;;

(defn get-hours-and-minutes [minutes]
  (let [h (.floor js/Math (/ minutes 60))
        m (- minutes (* 60 h))]
    [h m]))

(defn format-minutes [minutes]
  (let [[h m] (get-hours-and-minutes minutes)]
    (str h "h" m)))