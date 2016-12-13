(ns cljs-exponent.util
  "Helpful utility functions that don't fit anywhere else."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Util (aget exponent "Util"))

(defn get-current-locale-async
  "Returns the current device locale as a string."
  []
  (.call (aget Util "getCurrentLocaleAsync")
         Util))
