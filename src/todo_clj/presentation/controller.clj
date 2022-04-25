(ns todo-clj.presentation.controller
  (:require [ring.util.response :as response]
            [ring.middleware.params :as params])
  (:use [todo-clj.infrastructure.todo-dao]))

(defn from-todo [elm]
  "単一のTODOオブジェクトを平坦なオブジェクトにする"
  {:id (:value (:id elm))
   :title (:title elm)
   :user (:name (:user elm))
   :state (:value (:state elm))
   :created-at (:created-at elm)})

(defn to-response [result]
  "検索結果をハンドラのレスポンスに変換"
  (map from-todo result))

(defn get-todo-handler [req]
  "GETリクエストのメソッドハンドラ"
  (println (apply str "リクエスト => " (params/assoc-query-params req "utf-8")))
  (let [; クエリパラメータの解析、配列 in 配列の形式
        ; [[id 1] [size 1]]
        query-parameters (:params (params/assoc-query-params req "utf-8"))
        user-id (last (first query-parameters))
        result (if (nil? user-id)
                 (find-all (->InMemoryTodoDao todos))
                 (find-by-user (->InMemoryTodoDao todos) user-id))
        response (response/response (to-response result))]
    (println "レスポンス => " response)
    (response/content-type response "application/json")))

(defn to-request-body [req-body]
  "リクエストからマップに変換する"
  {:title (get-in req-body [:body "title"] "")
   :user-id (get-in req-body [:body "user-id"] "")})

(defn save-todo-handler [req]
  "POSTリクエストのメソッドハンドラ"
  (let [request-body {:title (get-in req [:body "title"] "")
                      :user-id (get-in req [:body "user-id"] "")}
        body (to-request-body (:body req))
        result (save (->InMemoryTodoDao todos) body)
        response-body (from-todo result)
        response (response/response response-body)]
    (println str "リクエスト =>" request-body)
    (response/content-type response "application/json")))
