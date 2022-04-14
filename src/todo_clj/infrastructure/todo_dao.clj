(ns todo-clj.infrastructure.todo-dao
  (:import (java.util Date))
  (:use [todo-clj.infrastructure.user-dao :as users]
        [clojure.set :as set]))

(def todos
  "todoセット"
  (let [now (Date.)]
  #{{:id 1 :user-id 1 :title "相談する" :created-at now}
    {:id 2 :user-id 1 :title "会議資料作成" :created-at now}
    {:id 3 :user-id 1 :title "コードレビュー" :created-at now}
    {:id 4 :user-id 1 :title "仕様の検討" :created-at now}
    {:id 5 :user-id 2 :title "メールの返信" :created-at now}}))

(defprotocol SearchTodoDao
  "TODO検索サービス"
  (find-by-user [user-id] "ユーザIDでTODOを絞る")
  (find-all [this] "すべてのTODOを返す"))

(defrecord InMemorySearchTodoDao []
  SearchTodoDao
  (find-all [this]
    (set/join todos users/users {:user-id :id}))
  (find-by-user [user-id]
    (let [todo-with-users (set/join todos users/users {:user-id :id})]
      (set/select #(= (:user-id) user-id) todo-with-users))))

