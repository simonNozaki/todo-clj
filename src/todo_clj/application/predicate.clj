(ns todo-clj.application.predicate)

(defn present?
  "値が存在することを確認する"
  [body]
  (not (nil? body)))
