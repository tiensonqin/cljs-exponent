(ns cljs-exponent.segment
  "Provides access to https://segment.com/ mobile analytics. Wraps Segment's iOS and Android sources.

  Note: Session tracking may not work correctly when running Experiences in the main Exponent app. It will work correctly if you create a standalone app."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Segment (aget exponent "Segment"))

(defn initialize-ios
  "Segment requires separate write keys for iOS and Android. Call this with the write key for your iOS source in Segment.

   Arguments
     writeKey (string) -- Write key for iOS source."
  [write-key]
  (.call (aget Segment "initializeIOS")
         Segment write-key))

(defn initialize-android
  "Segment requires separate write keys for iOS and Android. Call this with the write key for your Android source in Segment.

   Arguments:
     writeKey (string) -- Write key for Android source."
  [write-key]
  (.call (aget Segment "initializeAndroid")
         Segment write-key))

(defn identify
  "Associates the current user with a user ID. Call this after calling Exponent.Segment.initializeIOS() and Exponent.Segment.initializeAndroid() but before other segment calls. See https://segment.com/docs/spec/identify/."
  [user-id]
  (.call (aget Segment "identify")
         Segment user-id))

(defn identify-with-traits
  "Associates the current user with a user ID and some metadata. Call this after calling Exponent.Segment.initializeIOS() and Exponent.Segment.initializeAndroid() but before other segment calls. See https://segment.com/docs/spec/identify/.

   Arguments
     writeKey (string) -- User ID for the current user.

     :param object traits
     A map of custom properties."
  [user-id traits]
  (.call (aget Segment "identifyWithTraits")
         Segment user-id (clj->js traits)))

(defn track
  "Log an event to Segment. See https://segment.com/docs/spec/track/.

   Arguments
     event (string) -- The event name."
  [event]
  (.call (aget Segment "track")
         Segment event))

(defn trackWithProperties
  "Log an event to Segment with custom properties. See https://segment.com/docs/spec/track/.

   Arguments
     event (string) -- The event name.
     properties (object) -- A map of custom properties."
  [event properties]
  (.call (aget Segment "trackWithProperties")
         Segment event (clj->js properties)))

(defn flush
  "Manually flush the event queue. You shouldn't need to call this in most cases."
  []
  (.call (aget Segment "flush")
         Segment))
