(ns cljs-exponent.components
  #?(:clj (:require [clojure.string :as str]))
  #?(:cljs (:require [clojure.string :as str]
                     [cljs-exponent.core])))

(def components
  ["AppLoading"
   "BarCodeScanner"
   "BlurView"
   "LinearGradient"
   "MapView"
   "Svg"
   "Video"])

;; copy from natal-shell
(def camel-rx #"([a-z])([A-Z])")

(defn to-kebab [s]
  (-> s
      (str/replace camel-rx "$1-$2")
      (str/replace "." "-")
      str/lower-case))

(defn to-snake [s]
  (str/lower-case (str/replace s camel-rx "$1_$2")))

#?(:cljs
   (defn element [element opts & children]
     (apply (aget cljs-exponent.core/react "createElement") element (clj->js opts) children)))

#?(:clj
   (defn wrap-component [js-name]
     `(def ~(symbol (to-kebab js-name))
        (partial element (aget cljs-exponent.core/exponent "Components" ~js-name)))))

#?(:clj
   (defn wrap-glview []
     `(def ~'gl-view
        (partial element (aget cljs-exponent.core/exponent "GLView")))))

#?(:clj
   (defn wrap-reagent-component [js-name]
     `(def ~(symbol (to-kebab js-name))
        (reagent.core/adapt-react-class
         (cljs.core/aget cljs-exponent.core/exponent "Components" ~js-name)))))

#?(:clj
   (defn wrap-reagent-glview []
     `(def ~'gl-view
        (reagent.core/adapt-react-class
         (cljs.core/aget cljs-exponent.core/exponent "GLView")))))

#?(:clj
   (defmacro wrap-all-om []
     `(do
        ~(wrap-glview)
        ~@(map wrap-component components))))

#?(:clj
   (defmacro wrap-all-reagent []
     `(do
        ~(wrap-reagent-glview)
        ~@(map wrap-reagent-component components))))


;; TODO expose wrap-components api

;; (defmacro wrap-components [f components]
;;   `(do ~@(map ~f components)))

;; (defn wrap-all-om
;;   []
;;   (wrap-component wrap-om-component components))

;; (defn wrap-all-reagent
;;   []
;;   (wrap-component wrap-reagent-component components))
