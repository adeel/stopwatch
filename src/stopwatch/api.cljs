(ns stopwatch.api)

(defn- get-mins-elapsed [r]
  (let [start (r :start)
        end   (or (r :end) (js/Date.))]
    (Math/floor (/ (- (.getTime end) (.getTime start)) 60000))))

(defn summarize [rs]
  (let [projects (group-by :project rs)]
    (reduce
      (fn [m project]
        (assoc m project
          (apply + (map get-mins-elapsed (projects project)))))
      projects (keys projects))))

(defn start-project [project rs]
  (conj rs {:project project
            :start   (js/Date.)}))

(defn end [rs]
  (if-let [r (last rs)]
    (if-not (r :end)
      )))