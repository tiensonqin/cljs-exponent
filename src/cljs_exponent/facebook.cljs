(ns cljs-exponent.facebook
  "Provides Facebook integration for Exponent apps. Exponent exposes a minimal native API since you can access Facebook's Graph API directly through HTTP (using fetch, for example)."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Facebook (aget exponent "Facebook"))

(defn login-with-read-permissions-async
  "Prompts the user to log into Facebook and grants your app permission
to access their Facebook data.

   param string appId
     Your Facebook application ID. Facebook's developer documentation describes how to get one.

   param object options
     A map of options:

   permissions (array) -- An array specifying the permissions to ask for from Facebook for this login. The permissions are strings as specified in the Facebook API documentation. The default permissions are ['public_profile', 'email', 'user_friends'].
   returns
     If the user or Facebook cancelled the login, returns { type: 'cancel' }.
     Otherwise, returns { type: 'success', token, expires }. token is a string giving the access token to use with Facebook HTTP API requests. expires is the time at which this token will expire, as seconds since epoch. You can save the access token using, say, AsyncStorage, and use it till the expiration time."
  [app-id options]
  (.call (aget Facebook "logInWithReadPermissionsAsync")
         Facebook app-id (clj->js options)))
