(ns why.datomic.system
  (:require
   [com.stuartsierra.component :as component]
   [why.datomic.db :as db]))

(defn system
  [config]
  (component/system-using
   (component/system-map
    :config config
    :datomic (db/make-datomic (:datomic config)))
   {}))
