(ns atcoder-choice.db
  (:gen-class)
  (:require [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]))

;; TODO
;;  - 問題情報の SELECT クエリ
;;  - 問題情報の更新クエリ(定時実行・差分だけ？)

(def db-spec {:connection-uri (env :database-url)})
