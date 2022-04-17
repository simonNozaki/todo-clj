(ns todo-clj.infrastructure.todo-dao
  (:use [clojure.set :as set]
        [todo-clj.domain.todo]
        [todo-clj.domain.id]
        [todo-clj.domain.user]))

(def todos
  "todoセット"
  (let [bezos (->User (create-id 1) "Jeff Besoz")
        dissny (->User (create-id 2) "Walt Dissny")]
  #{(create-todo (create-id 1) "相談する" bezos (create-todo-state "UNPROCESSED"))
    (create-todo (create-id 2) "会議資料作成" bezos (create-todo-state "DONE"))
    (create-todo (create-id 3) "コードレビュー" dissny (create-todo-state "IN PROGRESS"))
    (create-todo (create-id 4) "仕様の検討" dissny (create-todo-state "IN PROGRESS"))
    (create-todo (create-id 5) "メール送信" dissny (create-todo-state "GONE"))}))

(defprotocol SearchTodoDao
  "TODO検索サービス"
  (find-by-user [this user-id] "ユーザIDでTODOを絞る")
  (find-all [this] "すべてのTODOを返す"))

(defrecord InMemorySearchTodoDao []
  SearchTodoDao
  (find-all [this]
    todos)
  (find-by-user [this user-id]
    ; IDが入力されてなければ空の結果
    (when (nil? user-id) #{})
    (println "ユーザID => " user-id)
    (set/select (fn [todo] (= (:value (:id (:user todo))) 1)) todos)))