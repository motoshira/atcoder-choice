(ns atcoder-choice.json
  (:require [cheshire.core :refer [refer-all]]
            [clj-http.client :as client]))


;; TODO
;;  - 問題の情報のfecth
;;  - 個人のSubmission情報のfetch

(def diff-info-api-url "https://kenkoooo.com/atcoder/resources/problem-models.json")
(def problem-info-api-url "https://kenkoooo.com/atcoder/resources/problems.json")

(defn make-user-submission-url [username]
  (join "https://kenkoooo.com/atcoder/atcoder-api/results?user="
        username))

(defn fetch-json [url]
  (try
    (client/get url
                {:headers {:accept-encoding "gzip"}})
    (catch Exception e
      (do
        (println (str "Error: " (.getMessage e)))
        {:error (.getMessage e)}))))
