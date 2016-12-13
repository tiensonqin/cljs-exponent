(ns cljs-exponent.contacts
  "Provides access to the phone's system contacts."
  (:require [cljs-exponent.core :refer [exponent]]))

(def Contacts (aget exponent "Contacts"))

(defn get-contacts-async
  "Get a list of all entries in the system contacts. This returns the name and optionally phone number and email of each contact.

  Arguments:
    fields (array) -- An array describing fields to retrieve per contact. Each element bust be one of Exponent.Contacts.PHONE_NUMBER or Exponent.Contacts.EMAIL.
  Returns:
    An array of objects of the form { id, name, phoneNumber, email } with phoneNumber and email only present if they were requested through the fields parameter."
  [fields]
  (.call (aget Contacts "getContactsAsync")
         Contacts (clj->js fields)))
