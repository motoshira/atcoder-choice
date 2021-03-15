(ns atcoder-choice.db
  (:gen-class)
  (:require [clojure.java.jdbc :as sql]
            [environ.core :refer [env]]))

(def database-url
  (env :database-url))

(def db-spec (env :database-url))
