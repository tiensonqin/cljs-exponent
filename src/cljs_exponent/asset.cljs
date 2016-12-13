(ns cljs-exponent.asset
  (:require [cljs-exponent.core :refer [exponent]]))

;; todo SPEC
(defn from-module
  "Returns the Exponent.Asset instance representing an asset given its module."
  [module]
  (let [Asset (aget exponent "Asset")]
    (.call (aget Asset "fromModule")
           module)))
