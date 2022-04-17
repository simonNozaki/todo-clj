(ns todo-clj.domain.id
  (:import java.util.UUID))

; IDオブジェクト
(defrecord Id [value])

(defn create-id
  "任意のIDオブジェクトを生成する。引数がない場合はランダムUUIDを設定"
  ([] (create-id (str (UUID/randomUUID))))
  ([value] (->Id value)))

