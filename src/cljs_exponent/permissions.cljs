(ns cljs-exponent.permissions
  "When it comes to adding functionality that can access potentially sensitive information on a user's device, such as their location, or possibly send them possibly unwanted push notifications, you will need to ask the user for their permission first. Unless you've already asked their permission, then no need. And so we have the Permissions module."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Permissions (aget exponent "Permissions"))

(defn get-async
  "Determines whether your app has already been granted access to the provided permission type.

   Arguments
     type (string) -- The name of the permission.

   Returns
     Returns a Promise that is resolved with the information about the permission, including status, expiration and scope (if it applies to the permission type)."
  [type]
  (.call (aget Permissions "getAsync")
         Permissions type))

(defn ask-async
  "Prompt the user for a permission. If they have already granted access, response will be success.

   Arguments
     type (string) -- The name of the permission.

   Returns
     Returns a Promise that is resolved with the information about the permission, including status, expiration and scope (if it applies to the permission type)."
  [type]
  (.call (aget Permissions "askAsync")
         Permissions type))

(def ^{:doc "The permission type for push notifications.
Note: On iOS, this does not disambiguate undetermined from denied and so will only ever return granted or undetermined. This is due to the way the underlying native API is implemented."}
  remote-notifications (aget Permissions "REMOTE_NOTIFICATIONS"))

(def ^{:doc "The permission type for location access."}
  location (aget Permissions "LOCATION"))
