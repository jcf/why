(ns why.datomic.db
  (:require
   [clojure.spec.alpha :as s]
   [com.stuartsierra.component :as component]
   [datomic.api :as d]
   [io.pedestal.log :as log]))

;; -----------------------------------------------------------------------------
;; API

;; Quack.
(defn- datomic?
  [x]
  (some-> x :conn #(instance? datomic.peer.Connection %)))

(defn db
  [datomic]
  {:pre [(datomic? datomic)]}
  (d/db (:conn datomic)))

;; -----------------------------------------------------------------------------
;; Component

(defrecord Datomic [conn uri]
  component/Lifecycle
  (start [c]
    (log/debug :in ::datomic :at :start :uri uri)
    (let [_ (d/create-database uri)
          conn (d/connect uri)]
      (assoc c :conn conn)))
  (stop [c]
    (log/debug :in ::datomic :at :stop)
    (assoc c :conn nil)))

(defmethod print-method Datomic
  [_ ^java.io.Writer w]
  (.write w "#<Datomic>"))

(s/def ::uri string?)
(s/def ::config (s/keys :req-un [::uri]))

(defn make-datomic
  [config]
  {:pre [(s/assert ::config config)]}
  (map->Datomic config))

