(ns todo-clj.presentation.controller
  (:require [ring.util.response :as response]
            [ring.middleware.params :as params])
  (:use [todo-clj.infrastructure.todo-dao]))

(defn to-response [result]
  "検索結果をハンドラのレスポンスに変換"
  (map (fn [r] {:id (:value (:id r))
                :title (:title r)
                :user (:name (:user r))
                :state (:value (:state r))
                :created-at (:created-at r)})
       result))

(defn get-handler [req]
  "GETリクエストのメソッドハンドラ"
  (println (apply str "リクエスト => " (params/assoc-query-params req "utf-8")))
  (let [; クエリパラメータの解析、配列 in 配列の形式
        ; [[id 1] [size 1]]
        query-parameters (:params (params/assoc-query-params req "utf-8"))
        user-id (last (first query-parameters))
        result (if (nil? user-id)
                 (find-all (->InMemorySearchTodoDao))
                 (find-by-user (->InMemorySearchTodoDao) user-id))
        response (response/response (to-response result))]
    (println "レスポンス => " response)
    (response/content-type response "application/json")))

(defn to-request-body [req-body]
  "リクエストからマップに変換する"
  {:title (get-in req-body [:body "title"])
   :user-id (get-in req-body [:body "id"])})

(defn post-handler [req]
  "POSTリクエストのメソッドハンドラ"
  (let [body (to-request-body (:body req))
        response (response/response {:message (apply str "Hello" body)})]
    (response/content-type response "application/json")))
