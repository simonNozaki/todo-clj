(ns todo-clj.main
  (:use [todo-clj.controller.core :as core]))

(core/start-server)

(core/restart-server)
