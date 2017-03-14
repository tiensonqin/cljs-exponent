(ns cljs-exponent.core)

(def react-native
  (when (exists? js/require)
    (js/require "react-native")))

(def react
  (when (exists? js/require)
    (js/require "react")))

(def exponent
  (when (exists? js/require)
    (js/require "expo")))

(def expo exponent)

(when react-native
  (set! js/window.React react))
