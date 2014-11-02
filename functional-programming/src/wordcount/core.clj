(ns wordcount.core
  (:require [wordcount.counter :as c]
            [wordcount.parser :as p]))

(defn -main [page-count]
  (c/count-words-parallel (p/pages page-count)))
