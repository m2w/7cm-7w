(defproject funcprog "0.1.0-SNAPSHOT"
  :description "Code for Chapter 3 of Seven Concurrency Models in Seven Weeks"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [compojure "1.2.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [clj-http "1.0.1"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
