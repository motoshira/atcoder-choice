(ns atcoder-choice.view
  (:gen-class)
  (:require [atcoder-choice.data :as data]
            [atcoder-choice.json :as j]
            [hiccup.core :as hc]
            [ring.util.http-response :as res]))

(def amount-min 1)
(def amount-max 20)


(defn render-view [& items]
  (-> items
      hc/html
      res/ok
      (res/content-type "text/html")))

(defn make-header [title]
  [:head
   [:meta {:charset "utf-8"}]
   [:link {:rel "stylesheet"
           :href "https://cdn.jsdelivr.net/npm/bulma@0.9.1/css/bulma.min.css"}]
   [:title title]])

(defn make-form-component [label-str control]
  [:div {:class "field"}
   [:label {:class "label"}
    label-str]
   [:div {:class "control"}
    control]])

(defn render-home-view [req]
  (render-view
   (make-header "AtCoder Choice")
   [:body
    [:section {:class "navbar is-black"}
     [:div {:class "navbar is-size-1 is-black"}
      [:a {:class "navbar-item"}
       "AtCoder Choice"]]]
    [:br]
    [:div {:class "block"}
     [:h3 "条件に合う問題をランダムに選択します。"]]
    [:section {:class "columns"}
     [:div {:class "column"}
      [:div {:class "box"}
       [:form {:action "/result"
               :method "get"}
        (make-form-component
         "ユーザー名"
         [:input {:class "control"
                  :name "Username"
                  :type "text"
                  :placeholder "motoshira"}])
        (make-form-component
         "問題数"
         [:div {:class "select"}
          [:select {:name "amount"}
           (for [x (range amount-min (inc amount-max))]
             [:option {:value x}
              (str x)])]])
        ;; TODO : checkbox component を切り出す
        (make-form-component
         "コンテスト種別"
         [:div {:class "checkbox"}
          (for [type data/contest-types]
            (list [:input {:type "checkbox"
                           :name (str type)}]
                  [:label {:for (str type)}
                   (str type)]
                  [:br]))])
        (make-form-component
         "Difficulty"
         [:div {:class "checkbox"}
          (for [diff (keys data/color-diffs)]
            (list
             [:input {:type "checkbox"
                      :name diff}]
             [:label {:for diff}
              diff]
             [:br]))])
        (make-form-component
         "提出状況"
         [:div {:class "checkbox"}
          (for [s data/problem-statuses]
            (let [status (str s)]
              (list
               [:input {:type "checkbox"
                        :name status}]
               [:label {:for status}
                status]
               [:br])))])
        [:input {:class "button go is-link"
                 :type "submit"
                 :value "Go"}]]]]]]))

;; TODO
;; - クエリパラメタのパース
;; - 条件に合うもんだのfilter
(defn render-result-view [req]
  (let [results (-> (:username req)
                    (j/make-user-submission-url)
                    (j/fetch-json))]
    (render-view
     (make-header "AtCoder Choice")
     [:body
      [:section {:class "navbar is-black"}
       [:div {:class "navbar is-size-1 is-black"}
        [:a {:class "navbar-item"}
         "AtCoder Choice"]]]
      [:section {:class "columns"}
       [:div {:class "column"}
        [:div {:class "box"}
         [:h1 "Result"]]]]])))
