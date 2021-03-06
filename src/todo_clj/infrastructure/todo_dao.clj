(ns todo-clj.infrastructure.todo-dao
  (:use [clojure.set :as set]
        [clojure.string :refer [blank?]]
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

(defprotocol TodoDao
  "TODO検索サービス"
  (save [this todo] "todoを追加する")
  (find-by-user [this user-id] "ユーザIDでTODOを絞る")
  (find-all [this] "すべてのTODOを返す"))

(defrecord InMemoryTodoDao [todos]
  TodoDao
  (save [this todo]
    (let [id (create-id)
          user-id (:user-id todo)
          ; todoの集合に入っているユーザのみ抜き出し
          maybe-user (first (set/select (fn [elm]
                                          (let [user-id-value (:value (:id (:user elm)))]
                                            (= user-id-value user-id))) todos))
          user (if (nil? maybe-user)
                 (->User (create-id) "Anonymous")
                 maybe-user)
          record (create-todo
                   (create-id)
                   (:title todo)
                   user
                   (create-todo-state))]
      (println maybe-user)
      (if (nil? maybe-user)
        (println "TODOの所有者がいません")
        record)))
  (find-all [this]
    todos)
  (find-by-user [this user-id]
    ; IDが入力されてなければ空の結果
    (when (nil? user-id) #{})
    (println "ユーザID => " user-id)
    (set/select (fn [todo]
                  (let [user-id-value (:value (:id (:user todo)))]
                    (= user-id-value user-id)))
                todos)))
