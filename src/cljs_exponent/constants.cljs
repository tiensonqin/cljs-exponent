(ns cljs-exponent.constants
  "System information that remains constant throughout the lifetime of your app."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Constants (aget exponent "Constants"))

(def ^{:doc "Returns exponent, standalone, or guest. If exponent, the experience is running inside of the Exponent client. If standalone, it is a standalone app. If guest, it has been opened through a link from a standalone app."}
  app-ownership (aget Constants "appOwnership"))

(def ^{:doc "The version string of the Exponent client currently running"}
  exponent-version (aget Constants "exponentVersion"))

(def ^{:doc "An identifier that is unique to this particular device and installation of the Exponent client."}
  device-id (aget Constants "deviceId"))

(def ^{:doc "A human-readable name for the device type."}
  device-name (aget Constants "deviceName"))

(def ^{:doc "The device year class of this device."}
  device-year-class (aget Constants "deviceYearClass"))

(def ^{:doc "true if the app is running on a device, false if running in a simulator or emulator."}
  is-device (aget Constants "true if the app is running on a device, false if running in a simulator or emulator."))

(def ^{:doc "Exponent.Constants.platform.
ios
platform
The Apple internal model identifier for this device, e.g. iPhone1,1.

model
The human-readable model name of this device, e.g. iPhone 7 Plus.

userInterfaceIdiom
The user interface idiom of this device, i.e. whether the app is running on an iPhone or an iPad. Current supported values are handset and tablet. Apple TV and CarPlay will show up as unsupported."}
  platform (aget Constants "platform"))

(def ^{:doc "true if the app is running on a device, false if running in a simulator or emulator."}
  session-id (aget Constants "true if the app is running on a device, false if running in a simulator or emulator."))

(def ^{:doc "The default status bar height for the device. Does not factor in changes when location tracking is in use or a phone call is active."}
  status-bar-height (aget Constants "statusBarHeight"))

(def ^{:doc "A list of the system font names available on the current device."}
  system-fonts (aget Constants "systemFonts"))

(def ^{:doc "The manifest object for the app, https://docs.getexponent.com/versions/v11.0.0/guides/how-exponent-works.html#exponent-manifest."}
  manifests (aget Constants "manifest"))

(def ^{:doc "When an app is opened due to a deep link, the prefix of the URI without the deep link part. This value depends on Exponent.Constants.appOwnership: it may be different if your app is running standalone vs. in the Exponent client."}
  linking-uri (aget Constants "linkingUri"))
