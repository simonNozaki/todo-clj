(ns todo-clj.infrastructure.user-dao
  (:import (java.util Date)))

(def users
  "ユーザセット"
  (let [now (Date.)]
    #{{:id 1 :name "Jeff Besoz" :registered-at now}
      {:id 2 :name "Walt Dissny" :registered-at now}}))
