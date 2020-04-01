(ns why.datomic.test.util
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.test]
   [com.stuartsierra.component :as component]
   [why.datomic.config :as config]
   [why.datomic.system :as system])
  (:import
   (java.util UUID)))

(defmacro isnt
  [& body]
  `(clojure.test/is (not ~@body)))

(defn ^String pprint-str
  [obj]
  (with-out-str (pprint obj)))

(defmacro with-system
  "Given a set of bindings, where the first value is a system definition, starts
  the system, executes `body`, and finally stops the system."
  [bindings & body]
  {:pre [(even? (count bindings))
         (<= 2 (count bindings))]}
  `(let [running# (component/start-system ~(second bindings))
         ~(first bindings) running#]
     (try
       ~@body
       (finally
         (component/stop-system running#)))))

(defn system
  []
  (-> (config/read-config)
      system/system
      (assoc-in [:datomic :uri] (str "datomic:mem://" (UUID/randomUUID)))
      (assoc-in [:service :http-port] 0)))
