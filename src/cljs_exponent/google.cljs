(ns cljs-exponent.google
  "Provides Google integration for Exponent apps. Exponent exposes a minimal native API since you can access Google's REST APIs directly through HTTP "
  (:require [cljs-exponent.core :refer [exponent]]))

(def Google (aget exponent "Google"))

(defn login-async
  "Prompts the user to log into Google and grants your app permission to access some of their Google data, as specified by the scopes.

  param object options
  A map of options:

  behavior (string) -- The type of behavior to use for login, either web or system. Native (system) can only be used inside of a standalone app when built using the steps described below. Default is web inside of Exponent app, and system in standalone.
  scopes (array) -- An array specifying the scopes to ask for from Google for this login (more information here). Default scopes are ['profile', 'email'].
webClientId (string) -- The client id registered with Google for the app, used with the web behavior.
  iosClientId (string) -- The client id registered with Google for the, used with the native behavior inside of a standalone app.
  returns:
    If the user or Google cancelled the login, returns { type: 'cancel' }.

    Otherwise, returns { type: 'success', accessToken, idToken, serverAuthCode, user: {...profileInformation} }. accessToken is a string giving the access token to use with Google HTTP API requests."
  [options]
  (.call (aget Google "logInAsync")
         Google
         (clj->js options)))
