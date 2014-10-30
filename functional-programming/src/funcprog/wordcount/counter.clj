(ns wordcount.counter)

(defn get-words [text]
  (re-seq #"\w+" text))

(defn count-word-sequential [pages]
  (frequencies (mapcat #(get-words (:text %)) pages)))
