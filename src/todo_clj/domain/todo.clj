(ns todo-clj.domain.todo
  (:import (java.util Date))
  (:use [todo-clj.domain.id]))

(defrecord TodoState [value])

; TODO状態コンストラクタ
(defn create-todo-state
  ([] (create-todo-state "UNPROCESSED"))
  ([value] (let [states #{"UNPROCESSED" "IN-PROGRESS" "DONE" "GONE"}
                 state (if (contains? states value) value "UNPROCESSED")]
    (->TodoState state))))

(defrecord Todo [id title user state created-at])

; TODOオブジェクトのコンストラクタ
(defn create-todo [id title user todo-state]
  (let [id (if (nil? id) (create-id) id)
        now (Date.)]
    (->Todo id title user todo-state now)))
