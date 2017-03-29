(ns cljs-exponent.components
  #?(:clj (:require [clojure.string :as str]))
  #?(:cljs (:require-macros [cljs-exponent.components :refer [wrap-all]]))
  #?(:cljs (:require [clojure.string :as str]
                     [cljs-exponent.core])))

;; React Native v0.36.0
(def rn-apis
  ["ActionSheetIOS"
   "AdSupportIOS"
   "Alert"
   "AlertIOS"
   "Animated"
   "AppRegistry"
   "AppState"
   "AsyncStorage"
   "BackAndroid"
   "CameraRoll"
   "Clipboard"
   "DatePickerAndroid"
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
   ;; "PushNotificationIOS"
   "Settings"
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
   "DatePickerIOS"
   "KeyboardAvoidingView"
   "DrawerLayoutAndroid"
   "Image"
   "ListView"
   "Modal"
   "Navigator"
   "NavigatorIOS"
   "Picker"
   "PickerIOS"
   "ProgressBarAndroid"
   "ProgressViewIOS"
   "RefreshControl"
   "ScrollView"
   "SegmentedControlIOS"
   "Slider"
   "SliderIOS"
   "StatusBar"
   "SnapshotViewIOS"
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
   "WebView"])

(def ex-components
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

(defn sp [js-name]
  (str/split js-name #"\."))

#?(:clj
   (defn wrap-rn-api [js-name]
     `(def ~(symbol (to-kebab js-name))
        (aget cljs-exponent.core/react-native ~js-name))))

#?(:cljs
   (defn element [element opts & children]
     (if element
       (apply (aget cljs-exponent.core/react "createElement") element (clj->js opts) children))))

#?(:clj
   (defn wrap-rn-component [js-name]
     (let [v (sp js-name)]
       (if (= 1 (count v))
         `(def ~(symbol (to-kebab js-name))
            (partial element (aget cljs-exponent.core/react-native ~js-name)))
         `(def ~(symbol (to-kebab js-name))
            (partial element (aget cljs-exponent.core/react-native ~(first v) ~(second v))))))))

#?(:clj
   (defn wrap-ex-component [js-name]
     `(def ~(symbol (to-kebab js-name))
        (partial element (aget cljs-exponent.core/exponent ~js-name)))))

#?(:clj
   (defn wrap-glview []
     `(def ~'gl-view
        (partial element (aget cljs-exponent.core/exponent "GLView")))))

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
