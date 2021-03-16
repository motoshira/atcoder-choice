(ns atcoder-choice.data
  (:gen-class)
  (:require [atcoder-choice.json :as j]
            [clojure.set :refer [rename-keys]]))

;; TODO
;;  - 問題のデータと提出データのマージ
;;  - 問題のデータはSQLで引っ張ってくる形にしたい

(def contest-types
  #{:abc
    :arc
    :agc
    :other})

(def color-diffs
  {:gray [0 399]
   :brown [400 799]
   :green [800 1199]
   :cyan [1200 1599]
   :blue [1600 1999]
   :yellow [2000 2399]
   :orange [2400 2799]
   :red [2800 3199]
   :bronze [3200 3599]
   :silver [3600 3999]
   :gold [4000 9999]})

;; 優先度の最も高いものを表示したい
(def status-and-priorities
  {:ac 0
   :tle 1
   :mle 2
   :wa 3
   :re 4
   :ce 5
   :ole 6
   :ie 7
   :not-yet 8})

(def fetch-and-merge-problem-and-diff-info
  (memoize
   (fn [req]
     (let [diff-info (j/fetch-json j/diff-info-api-url)]
       (->> (j/fetch-json j/problem-info-api-url)
            (map #(rename-keys % {:id :problem-id}))
            (map #(->> (:problem-id %)
                       (get diff-info)
                       (:difficulty)
                       (assoc % :difficulty))))))))

(defn classify-contest-type [info]
  (let [type (-> (:contest-id info)
                 (keyword))]
    (if (find type contest-types)
      type
      :other)))

(defn fetch-user-submission-info [username]
  (->> username
       (j/make-user-submission-url)
       (j/fetch-json)
       (map #(-> %
                 (rename-keys {:id :problem-id})
                 (assoc :contest-type (classify-contest-type %))))
       (filter #(-> (:contest-type %)
                    (find contest-types)))))

(defn fetch-results [req]
  (let [problem-info (fetch-and-merge-problem-and-diff-info)
        user-sub-info (fetch-user-submission-info (:username req))]
    (for [info problem-info]
      (let [status (->> (reduce (fn [val m]
                                  (min val
                                       (->> (:result m)
                                            (get status-and-priorities))))
                                (:not-yet status-and-priorities)
                                user-sub-info)
                        (get status-and-priorities))]
        (assoc info :status status)))))
