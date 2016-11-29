(ns cljs-exponent.reagent
  (:require-macros [cljs-exponent.components :refer [wrap-all-reagent]])
  (:require [cljs-exponent.core]
            [reagent.core]))

(defn safe-adapt-react-class [component]
  (if component
    (reagent.core/adapt-react-class component)))

(wrap-all-reagent)
