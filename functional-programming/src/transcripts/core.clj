(ns transcripts.core
  (:require [clojure.edn :as edn]
            [compojure.core :refer [GET PUT POST defroutes]]
            [compojure.handler :refer [api]]
            [ring.util.response :refer [response charset status]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clj-http.client :as client]
            [transcripts.sentences :refer :all]))

(def snippets (repeatedly promise))

(def sonnet18->german
  {"Shall I compare thee to a summer's day?"
   "Soll ich Dich einem Sommertag vergleichen?",
   "Thou art more lovely and more temperate:"
   "Nein, Du bist lieblicher und frischer weit -",
   "Rough winds do shake the darling buds of May,"
   "Durch Maienblüthen rauhe Winde streichen",
   "And summer's lease hath all too short a date:"
   "Und kurz nur währt des Sommers Herrlichkeit.",

   "Sometime too hot the eye of heaven shines,"
   "Zu feurig oft läßt er sein Auge glühen,",
   "And often is his gold complexion dimm'd;"
   "Oft auch verhüllt sich seine goldne Spur,",
   "And every fair from fair sometime declines,"
   "Und seiner Schönheit Fülle muß verblühen",
   "By chance or nature's changing course untrimm'd;"
   "Im nimmerruh'nden Wechsel der Natur.",

   "But thy eternal summer shall not fade"
   "Nie aber soll Dein ewiger Sommer schwinden,",
   "Nor lose possession of that fair thou owest;"
   "Die Zeit wird Deiner Schönheit nicht verderblich,",
   "Nor shall Death brag thou wander'st in his shade,"
   "Nie soll des neidischen Todes Blick Dich finden,",
   "When in eternal lines to time thou growest:"
   "Denn fort lebst Du in meinem Lied unsterblich.",

   "So long as men can breathe or eyes can see,"
   "So lange Menschen athmen, Augen sehn,",
   "So long lives this, and this gives life to thee."
   "Wirst Du, wie mein Gesang, nicht untergehn."
   })

(def translator "http://localhost:3001/translate")

(defn accept-snippet [n text]
  (deliver (nth snippets n) text))

(defn translate [text]
  (future (:body (client/post translator {:body text}))))

(def translations
  (delay
   (map translate (strings->sentences (map deref snippets)))))

(defn get-translation [n]
  @(nth @translations n))

(defroutes app-routes
  (PUT "/snippet/:n" [n :as {:keys [body]}]
       (accept-snippet (edn/read-string n) (slurp body))
       (response "OK"))
  (GET "/translation/:n" [n]
       (let [s (edn/read-string n)]
         (if (realized? (nth snippets s))
           (response (get-translation s))
           (status (response "Not translated yet.") 409)))))

(defroutes translation-routes
  (POST "/translate" [c :as {:keys [body]}]
        (response (get sonnet18->german (slurp body)
                       "For now we only translate Shakespear's 18th sonnet."))))

(defn wrap-charset [handler]
     (fn [req] (charset (handler req) "UTF-8")))

(defonce snippets-server
  (run-jetty (wrap-charset (api app-routes)) {:port 3000
                                              :join? false}))

(defonce translation-server
  (run-jetty (wrap-charset (api translation-routes)) {:port 3001
                                                      :join? false}))
