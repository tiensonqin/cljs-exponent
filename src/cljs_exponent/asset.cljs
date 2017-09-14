(ns cljs-exponent.asset
  (:require [cljs-exponent.core :refer [expo]]))

(defn from-module
  "Returns the Exponent.Asset instance representing an asset given its module."
  [module]
  (let [Asset (aget expo "Asset")]
    (.call (aget Asset "fromModule")
           module)))

(defn- cache-images
  [images]
  (for [image images]
    (when image
      (.loadAsync (aget expo "Asset") image))))

(defn- cache-fonts
  [fonts]
  (for [font fonts]
    (when font
      (.loadAsync (aget expo "Font") font))))

(defn- cast-as-array
  [coll]
  (if (or (array? coll)
          (not (reduceable? coll)))
    coll
    (into-array coll)))

(defn all
  [coll]
  (.all js/Promise (cast-as-array coll)))

(defn cache-assets
  [images fonts cb]
  (->
   (all (concat (cache-fonts (clj->js fonts)) (cache-images (clj->js images))))
   (.then (fn [resp]
            (if cb (cb))))
   (.catch (fn [err]
             (println "Loading assets failed: " (aget err "message"))))))
