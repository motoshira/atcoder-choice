(ns ach-api.data
  (:gen-class))


(def contest-types
  ["abc"
   "arc"
   "agc"])

(def color-diffs
  {"gray" [0 399]
   "brown" [400 799]
   "green" [800 1199]
   "cyan" [1200 1599]
   "blue" [1600 1999]
   "yellow" [2000 2399]
   "orange" [2400 2799]
   "red" [2800 3199]
   "bronze" [3200 3599]
   "silver" [3600 3999]
   "gold" [4000 9999]})

(def problem-statuses
  ["not-yet"
   "trying"
   "solved"])
