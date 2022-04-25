(ns todo-clj.main
  (:use [ring.adapter.jetty :as server]
        [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
        [ring.middleware.keyword-params :refer [wrap-keyword-params]]
        [ring.middleware.params :refer [wrap-params]]
        [ring.logger :as logger]
        [compojure.core :refer :all]
        [compojure.route]
        [todo-clj.presentation.controller]
        [todo-clj.application.middleware.exception-handler]))

(defonce server (atom nil))

(defn handle-request [handler]
  (-> handler
      logger/wrap-log-response
      logger/wrap-log-request-start
      wrap-keyword-params
      wrap-params
      wrap-json-response
      wrap-json-body
      wrap-handler-error))

(defroutes app-routes
           "ルーティング"
           (GET "/todo" req (handle-request get-todo-handler))
           (POST "/todo" req (handle-request save-todo-handler)))

(defn start-server []
  "サーバの起動"
  (println "サーバを開始します")
  (when-not @server
    (reset! server (server/run-jetty #'app-routes {:port 3000 :join? false}))))

(defn stop-server []
  (println "サーバを終了します")
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))


(start-server)

(restart-server)
