(ns wordcount.counter
  (:require [wordcount.reducer :as r]))

(defn- get-words-re [text]
  (re-seq #"\w+" text))

(defn get-words [page]
  (get-words-re (:text page)))

(defn count-words-sequential [pages]
  (frequencies (mapcat get-words pages)))

(defn count-words-parallel [pages]
  (reduce (partial merge-with +)
          (pmap #(frequencies (get-words %)) pages)))

(defn count-words-partitioned [pages]
  (reduce (partial merge-with +)
          (pmap count-words-sequential (partition-all 100 pages))))

(defn parallel-frequencies [coll]
  (r/my-fold
   (partial merge-with +)
   (fn [counts x] (assoc counts x (inc (get counts x 0))))
   coll))
