(ns sum
  (:require [clojure.core.reducers :as r]))

(defn recursive-sum [numbers]
  (loop [sum 0 n numbers]
    (if (empty? n)
      sum
      (recur (+ (first n) sum) (rest n)))))

(defn apply-sum [numbers]
  (apply + numbers))

(defn reduce-sum [numbers]
  (reduce #(+ %1 %2) 0 numbers))

(defn sum [numbers]
  (reduce + numbers))

(defn parallel-sum [numbers]
  (r/fold + numbers))
