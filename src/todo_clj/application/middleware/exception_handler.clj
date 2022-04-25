(ns todo-clj.application.middleware.exception-handler
  (:use [ring.util.response :as response]))

; エラーオブジェクト
(defrecord ApplicationError [id message])


(defn wrap-handler-error [handler]
  "例外をハンドリングするミドルウェア"
  (try
    (fn [request]
      (handler request))
      (catch Exception e
        (println str "予期しないエラー" (.printStackTrace e))
        (-> response/response (->ApplicationError "予期しないエラーが発生しました")
            response/status 500))))
