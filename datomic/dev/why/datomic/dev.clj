(ns ^{:clj-kondo/config
      {:unused-namespace {:exclude '[datomic.api]}}}
    why.datomic.dev
  "Default namespace used after you've booted the dev system. Provides useful
  requires, utilities for use in development."
  (:require
   [clojure.tools.namespace.repl
    :as tns.repl
    :refer [refresh refresh-all clear]]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [datomic.api :as d]
   [why.datomic.config :as config]
   [why.datomic.db :as db]
   [why.datomic.system :as system]))

(set-init
 (fn start-dev
   [_]
   (system/system (config/read-config))))

(def db (comp db/db :datomic))

(comment
  (:datomic system))
