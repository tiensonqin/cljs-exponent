(ns cljs-exponent.font
  "Allows loading fonts from the web and using them in React Native components."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Font (aget exponent "Font"))

(defn load-async
  ([name url]
   "Load a font from the web and associate it with the given name.

  Arguments:
    name (string) -- A name by which to identify this font. You can make up any name you want; you just have to specify the same name in Exponent.Font.style() to use this font.

  Returns:
    Doesn't return anything and simply awaits till the font is available to use."
   (.loadAsync Font name url))
  ([fonts-map]
   "Convenience form of Exponent.Font.loadAsync() that loads multiple fonts at once.

   Arguments:
   map (object) -- A map of names to urls as in Exponent.Font.loadAsync().
   Returns:
   Doesn't return anything and simply awaits till all fonts are available to use."
   (.loadAsync Font (clj->js fonts-map))))

(defn style
  "Return style properties to use with a Text or other React Native component. It is safe to call this function before calling Exponent.Font.loadAsync(); it will still return the correct style properties. This way you can use this function with StyleSheet.create().

   Arguments:
     name (string) -- The name for this font specified in Exponent.Font.loadAsync().
   Returns:
     An object with style attributes to use in a Text or similar component."
  [name]
  (.style Font name))
