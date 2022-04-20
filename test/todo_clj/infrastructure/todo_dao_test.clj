(ns todo-clj.infrastructure.todo-dao-test
  (:use [clojure.test])
  (:use [todo-clj.infrastructure.todo-dao]
        [todo-clj.application.functions])
  (:require [clojure.set :as set]))

(deftest todo-dao-find-all-test
  (testing "結果を取得できる"
    (is (present? (find-all (->InMemoryTodoDao))))))

(deftest todo-dao-find-by-user-test
  (testing "ID=1で検索結果あり"
    (println (find-by-user (->InMemoryTodoDao) 1))
    (is (present? (find-by-user (->InMemoryTodoDao) 1))))
  (testing "IDが入ってなければ空の結果を返す"
    (is (= (empty? (find-by-user (->InMemoryTodoDao) nil)) true))))

(deftest todo-dao-save-test
  (testing "レコードを登録できる"
    (is (let [req {:title "テスト登録" :user-id 1}
              result (save (->InMemoryTodoDao) req)]
          (present? result)
          (present? (set/select (fn [r] (= (:title r) "テスト登録")) result))))))
