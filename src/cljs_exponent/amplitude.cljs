(ns cljs-exponent.amplitude
  "Provides access to https://amplitude.com/ mobile analytics. Wraps Amplitude's iOS and Android SDKs."
  (:require [cljs-exponent.core :refer [exponent]]))

(defonce Amplitude (aget exponent "Amplitude"))

(defn initialize
  "Initializes Amplitude with your Amplitude API key. Find your API key using these instructions."
  [api-key]
  (.call (aget Amplitude "initialize")
         Amplitude api-key))

(defn set-user-id
  "Assign a user ID to the current user. If you don't have a system for user IDs you don't need to call this. See https://amplitude.zendesk.com/hc/en-us/articles/206404628-Step-2-Assign-User-IDs-and-Identify-your-Users."
  [user-id]
  (.call (aget Amplitude "setUserId")
         Amplitude api-key))

(defn set-user-properties
  "Set properties for the current user. See https://amplitude.zendesk.com/hc/en-us/articles/207108327-Step-4-Set-User-Properties-and-Event-Properties."
  [user-properties]
  (.call (aget Amplitude "setUserProperties")
         Amplitude (clj->js user-properties)))

(defn clear-user-properties
  "Clear properties set by Exponent.Amplitude.setUserProperties()."
  []
  (.call (aget Amplitude "clearUserProperties")
         Amplitude))

(defn log-event
  "Log an event to Amplitude. https://amplitude.zendesk.com/hc/en-us/articles/206404698-Step-3-Track-Events-and-Understand-the-Actions-Users-Take has information about what kind of events to track."
  [event-name]
  (.call (aget Amplitude "logEvent")
         Amplitude event-name))

(defn log-event-with-properties
  "Log an event to Amplitude with custom properties. https://amplitude.zendesk.com/hc/en-us/articles/206404698-Step-3-Track-Events-and-Understand-the-Actions-Users-Take has information about what kind of events to track."
  [event-name properties]
  (.call (aget Amplitude "logEventWithProperties")
         Amplitude event-name (clj->js properties)))

(defn set-group
  "Add the current user to a group. See https://github.com/amplitude/Amplitude-iOS#setting-groups and https://github.com/amplitude/Amplitude-Android#setting-groups."
  [group-type group-names]
  (.call (aget Amplitude "setGroup")
         Amplitude group-type group-names))
