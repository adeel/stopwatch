(defproject stopwatch "1.0.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :description  "FIXME: write description"
  :cljsbuild    {:builds [{:source-path "src"
                           :compiler    {:output-to     "stopwatch.js"
                                         :target        :nodejs
                                         :optimizations :simple
                                         :pretty-print  true}}]}
  :plugins      [[lein-cljsbuild "0.1.10"]])