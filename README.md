# cljs-exponent

[Exponent.js](https://getexponent.com/) and React Native' clojurescript binding.

[documentation](https://tiensonqin.github.io/cljs-exponent)

[![Clojars Project](https://img.shields.io/clojars/v/cljs-exponent.svg)](https://clojars.org/cljs-exponent)

## Usage examples

### Api

``` clojure
(require '[cljs-exponent.contacts :as contacts])

(contacts/get-contacts-async [(aget contacts/Contacts "PHONE_NUMBER")
                              (aget contacts/Contacts "EMAIL")])
```

### Components and APIs
Supports both [Om](https://github.com/omcljs/om), [Reagent](https://github.com/reagent-project/reagent) and [Rum](https://github.com/tonsky/rum).

#### Om or Rum

``` clojure
(require '[cljs-exponent.components :as rn])

(rn/text "hi")

(.alert rn/alert "This is an alert!")

(rn/linear-gradient
 {:colors ["#4c669f" "#3b5998" "#192f6a"]
  :style {:padding 15
          :alignItems "center"
          :borderRadius 5}}
 (text {:style {:backgroundColor "transparent"
                :fontSize 15
                :color "#fff"}}
   "Sign in with Facebook"))
```

#### Reagent

``` clojure
(require '[cljs-exponent.reagent :as rn])

[rn/text "hi"]

(.alert rn/alert "This is an alert!")

[rn/linear-gradient
 {:colors ["#4c669f" "#3b5998" "#192f6a"]
  :style {:padding 15
          :align-items "center"
          :border-radius 5}}
 [text {:style {:background-color "transparent"
                :font-size 15
                :color "#fff"}}
  "Sign in with Facebook"]]
```

## License

Copyright © 2016 Tienson Qin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
