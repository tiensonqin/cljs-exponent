(ns cljs-exponent.om
  (:require-macros [cljs-exponent.components :refer [wrap-all-om]])
  (:require [cljs-exponent.core]))

(defn element [element opts & children]
  (if element
    (apply (aget cljs-exponent.core/react "createElement") element (clj->js opts) children)))

(wrap-all-om)
