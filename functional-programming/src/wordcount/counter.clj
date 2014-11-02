(ns wordcount.counter)

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
