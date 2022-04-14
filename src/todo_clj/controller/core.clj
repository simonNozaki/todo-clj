(ns todo-clj.controller.core
  (:require [ring.adapter.jetty :as server]
            [ring.util.response :as response]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [compojure.core :refer :all]
            [compojure.route :as route])
  (:use [todo-clj.infrastructure.todo-dao]))

(defonce server (atom nil))

(defn get-handler [req]
  "POSTリクエストのメソッドハンドラ"
  (println req)
  (let [search-todo (->InMemorySearchTodoDao)
        response (response/response (find-all search-todo))]
    (response/content-type response "application/json")))

(defn post-handler [req]
  "POSTリクエストのメソッドハンドラ"
  (println (apply str "リクエスト => " (:body req)))
  (let [body (get-in req [:body "name"] "指定されていません")
        response (response/response {:message (apply str "Hello" body)})]
    (response/content-type response "application/json")))

(defn handle-request [handler]
  "ハンドラのJSONレスポンスラッパ"
  (wrap-json-body (wrap-json-response handler) handler {:keywords? true}))

(defroutes app-routes
           "ルーティング"
           (GET "/" req (handle-request get-handler))
           (POST "/" req (handle-request post-handler)))

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
