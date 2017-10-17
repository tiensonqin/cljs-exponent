(ns cljs-exponent.components
  #?(:clj (:require [clojure.string :as str]
                    [clojure.walk :as w]
                    [clojure.set :as set]))
  #?(:cljs (:require-macros [cljs-exponent.components :refer [wrap-all]]))
  #?(:cljs (:require [clojure.string :as str]
                     [cljs-exponent.core]
                     [clojure.walk :as w]
                     [clojure.set :as set])))

(def rn-apis
  ["AccessibilityInfo"
   "ActionSheetIOS"
   "Alert"
   "AlertIOS"
   "Animated"
   "AppRegistry"
   "AppState"
   "AsyncStorage"
   "BackAndroid"
   "BackHandler"
   "CameraRoll"
   "Clipboard"
   "Dimensions"
   "Easing"
   "Geolocation"
   "ImageEditor"
   "ImagePickerIOS"
   "ImageStore"
   "IntentAndroid"
   "InteractionManager"
   "Keyboard"
   "LayoutAnimation"
   "Linking"
   "NativeMethodsMixin"
   "NativeModules"
   "NetInfo"
   "PanResponder"
   "PermissionsAndroid"
   "PixelRatio"
   "Platform"
   "Settings"
   "Share"
   "StatusBarIOS"
   "StyleSheet"
   "Systrace"
   "TimePickerAndroid"
   "ToastAndroid"
   "Vibration"
   "VibrationIOS"])

(def rn-components
  ["ActivityIndicator"
   "Animated.Image"
   "Animated.Text"
   "Animated.View"
   "Animated.ScrollView"
   "Button"
   "DatePickerIOS"
   "DrawerLayoutAndroid"
   "Image"
   "KeyboardAvoidingView"
   "ListView"
   "FlatList"
   "MaskedViewIOS"
   "Modal"
   "NavigatorIOS"
   "Picker"
   "PickerIOS"
   "ProgressBarAndroid"
   "ProgressViewIOS"
   "RefreshControl"
   "ScrollView"
   "SectionList"
   "SegmentedControlIOS"
   "Slider"
   "SnapshotViewIOS"
   "StatusBar"
   "Switch"
   "TabBarIOS"
   "TabBarIOS.Item"
   "Text"
   "TextInput"
   "ToolbarAndroid"
   "TouchableHighlight"
   "TouchableNativeFeedback"
   "TouchableOpacity"
   "TouchableWithoutFeedback"
   "View"
   "ViewPagerAndroid"
   "VirtualizedList"
   "WebView"])

;; TODO full expo components and api support
(def ex-components
  ["AppLoading"
   "Assets"
   "Font"
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

(defn sp [js-name]
  (str/split js-name #"\."))

(defn kebab-case->camel-case
  "Converts from kebab case to camel case, eg: on-click to onClick"
  [input]
  (let [words (str/split input #"-")
        capitalize (->> (rest words)
                        (map #(apply str (str/upper-case (first %)) (rest %))))]
    (apply str (first words) capitalize)))

(defn map-keys->camel-case
  "Stringifys all the keys of a cljs hashmap and converts them
   from kebab case to camel case. If :html-props option is specified,
   then rename the html properties values to their dom equivalent
   before conversion"
  [data & {:keys [html-props]}]
  (let [convert-to-camel (fn [[key value]]
                           [(kebab-case->camel-case (name key)) value])]
    (w/postwalk (fn [x]
                  (if (map? x)
                    (let [new-map (if html-props
                                    (set/rename-keys x {:class :className :for :htmlFor})
                                    x)]
                      (into {} (map convert-to-camel new-map)))
                    x))
                data)))

#?(:clj
   (defn wrap-rn-api [js-name]
     `(def ~(symbol (to-kebab js-name))
        (aget cljs-exponent.core/react-native ~js-name))))

#?(:cljs
   (defn element [element opts & children]
     (if element
       (apply (aget cljs-exponent.core/react "createElement") element
         (clj->js (map-keys->camel-case opts :html-props true))
         children))))

#?(:cljs
   (defn partial-element
     [& args]
     (-> (apply partial element args)
         (with-meta {:rn-element? true}))))

#?(:clj
   (defn wrap-rn-component [js-name]
     (let [v (sp js-name)]
       (if (= 1 (count v))
         `(def ~(symbol (to-kebab js-name))
            (partial-element (aget cljs-exponent.core/react-native ~js-name)))
         `(def ~(symbol (to-kebab js-name))
            (partial-element (aget cljs-exponent.core/react-native ~(first v) ~(second v))))))))

#?(:clj
   (defn wrap-ex-component [js-name]
     `(def ~(symbol (to-kebab js-name))
        (partial-element (aget cljs-exponent.core/exponent ~js-name)))))

#?(:clj
   (defn wrap-glview []
     `(def ~'gl-view
        (partial-element (aget cljs-exponent.core/exponent "GLView")))))

#?(:clj
   (defn wrap-rn-reagent-component [js-name]
     (let [v (sp js-name)]
       (if (= 1 (count v))
         `(def ~(symbol (to-kebab js-name))
            (cljs-exponent.reagent/safe-adapt-react-class
             (cljs.core/aget cljs-exponent.core/react-native ~js-name)))
         `(def ~(symbol (to-kebab js-name))
            (cljs-exponent.reagent/safe-adapt-react-class
             (aget cljs-exponent.core/react-native ~(first v) ~(second v))))))))

#?(:clj
   (defn wrap-ex-reagent-component [js-name]
     `(def ~(symbol (to-kebab js-name))
        (cljs-exponent.reagent/safe-adapt-react-class
         (cljs.core/aget cljs-exponent.core/exponent ~js-name)))))

#?(:clj
   (defn wrap-reagent-glview []
     `(def ~'gl-view
        (cljs-exponent.reagent/safe-adapt-react-class
         (cljs.core/aget cljs-exponent.core/exponent "GLView")))))

#?(:clj
   (defmacro wrap-all []
     `(do
        ~@(map wrap-rn-api rn-apis)
        ~@(map wrap-rn-component rn-components)
        ~@(map wrap-ex-component ex-components)
        ~(wrap-glview))))

#?(:clj
   (defmacro wrap-all-reagent []
     `(do
        ~@(map wrap-rn-api rn-apis)
        ~@(map wrap-rn-reagent-component rn-components)
        ~@(map wrap-ex-reagent-component ex-components)
        ~(wrap-reagent-glview))))

#?(:cljs
   (wrap-all))

;; utils
#?(:cljs
   (defn ios?
     []
     (= "ios" (aget platform "OS"))))

#?(:cljs
   (defn android?
     []
     (= "android" (aget platform "OS"))))
