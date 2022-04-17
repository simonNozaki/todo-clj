(ns todo-clj.domain.todo-test
  (:require [clojure.test :refer :all])
  (:use [todo-clj.domain.todo]))

(deftest todo-state-test
  (testing "TODOの状態を初期化できる"
    (is (= "UNPROCESSED" (:value (create-todo-state)))))
  (testing "任意の状態でレコードを生成できる")
    (println (:value (create-todo-state "IN-PROGRESS")))
    (is (= "IN-PROGRESS" (:value (create-todo-state "IN-PROGRESS"))))
  (testing "定義されていない状態を許容しない")
    (println (:value (create-todo-state "完了")))
    (is (= "UNPROCESSED" (:value (create-todo-state "完了")))))
