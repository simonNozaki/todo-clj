(ns todo-clj.infrastructure.todo-dao-test
  (:use [clojure.test])
  (:use [todo-clj.infrastructure.todo-dao]
        [todo-clj.application.predicate]))

(deftest search-todo-dao-find-all-test
  (testing "結果を取得できる"
    (is (present? (find-all (->InMemorySearchTodoDao))))))

(deftest search-todo-dao-find-by-user-test
  (testing "ID=1で検索結果あり"
    (println (find-by-user (->InMemorySearchTodoDao) 1))
    (is (present? (find-by-user (->InMemorySearchTodoDao) 1))))
  (testing "IDが入ってなければ空の結果を返す"
    (is (= (empty? (find-by-user (->InMemorySearchTodoDao) nil)) true))))