(ns wordcount.core
  (:require [wordcount.counter :as c]
            [wordcount.parser :as p]))

(defn -main [page-count]
  (c/count-word-sequential (p/pages page-count)))
