(ns ach-api.core
  (:gen-class)
  (:require [ach-api.view :as view]
            [ring.adapter.jetty :as server]
            [bidi.bidi :as b]
            [bidi.ring :as r]))

(defonce server (atom nil))


(def routes
  ["/" {"home" {:get view/render-home-view}}
   {"result" {:get view/render-result-view}}])

(def handler
  (r/make-handler routes))

(defn start-server []
  (when-not @server
    (reset! server (server/run-jetty handler {:port 3001
                                              :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn -main
  [& args]
  (start-server))
