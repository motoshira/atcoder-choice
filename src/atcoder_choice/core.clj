(ns atcoder-choice.core
  (:gen-class)
  (:require [atcoder-choice.view :as view]
            [ring.adapter.jetty :as server]
            [ring.logger :as r.l]
            [ring.middleware.params :as r.m.p]
            [ring.middleware.keyword-params :as r.m.kw]
            [ring.util.http-response :as res]
            ;; [bidi.bidi :as b]
            [bidi.ring :as b.r]))


;; TODO : integrant で依存関係を分離したコードに修正する

;; TODO : "/result" クエリパラメータの扱い

(def routes
  ["/" {"home" {:get view/render-home-view}}
       {"result" {:get view/render-result-view}}])

;; Reference : https://tech.uzabase.com/entry/2019/10/07/190000
;; TODO : 例外の種類に応じたresponseの実装
(def wrap-exception-handler [handler]
  (fn exception-handler [req]
    (try
      (handler req)
      (catch Exception e
        (-> (res/response "Internal Server Error")
            (res/status 500))))))

(def handler
  (-> (b.r/make-handler routes)
      (r.m.kw/wrap-keyword-params)
      (r.m.p/wrap-params)
      (r.l/wrap-with-logger)
      (wrap-exception-handler)))



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
