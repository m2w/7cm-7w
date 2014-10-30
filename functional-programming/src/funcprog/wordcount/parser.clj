(ns wordcount.parser
  (:require [clojure.data.zip.xml :as zxml :refer [xml1-> text]]
            [clojure.zip :as zip :refer [xml-zip]]
            [clojure.data.xml :as xml :refer [parse]]
            [clojure.java.io :as io]))

(def xml-path "/Users/blah/Downloads/simplewiki-20141025-pages-meta-current.xml")

(defn page->map [page]
  (let [z (xml-zip page)]
    {:title (xml1-> z :title text)
     :text (xml1-> z :revision :text text)}))

(defn words [page-map]
  (re-seq #"\w+" (:text page-map)))

(defn pages [page-count]
  (with-open [r (io/reader xml-path)]
    (doall
     (take page-count
           (->> r parse :content (filter #(= :page (:tag %))) (map page->map))))))
