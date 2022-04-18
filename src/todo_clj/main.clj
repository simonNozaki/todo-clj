(ns todo-clj.main
  (:use [ring.adapter.jetty :as server]
        [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
        [compojure.core :refer :all]
        [compojure.route]
        [todo-clj.presentation.controller]))

(defonce server (atom nil))

(defn handle-request [handler]
  "ハンドラのJSONレスポンスラッパ、リクエスト・レスポンスをマップ・JSONに変換する"
  (wrap-json-body (wrap-json-response handler) handler {:keywords? true}))

(defroutes app-routes
           "ルーティング"
           (GET "/todo" req (handle-request get-handler))
           (POST "/todo" req (handle-request post-handler)))

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
