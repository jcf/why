(ns why.datomic.config)

(defn read-config
  []
  {:datomic {:uri "datomic:mem://why-not"}})
