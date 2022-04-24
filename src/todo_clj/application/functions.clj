(ns todo-clj.application.functions)

(defn present?
  "コレクションが空でないことを確認する"
  [body]
  (not (nil? (seq body))))