(ns todo-clj.infrastructure.todo-dao-test
  (:use [clojure.test])
  (:use [todo-clj.infrastructure.todo-dao]))

(deftest search-todo-dao-find-all-test
  (deftest "結果を取得できる"
    (is (= 0 0))))

(deftest search-todo-dao-find-by-user-test
  (testing "ID=1で検索結果あり"
    (println (find-by-user (->InMemorySearchTodoDao) 1))
    (is (= (empty? (find-by-user (->InMemorySearchTodoDao) 1)) false))))
  (testing "IDが入ってなければ空の結果を返す"
    (is (= (empty? (find-by-user (->InMemorySearchTodoDao) nil)) true)))
