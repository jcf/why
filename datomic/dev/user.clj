(ns user
  "Minimal REPL setup to ensure you get a REPL regardless of whether some things
  are broken."
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as stest]
   [clojure.tools.namespace.repl
    :as tns.repl
    :refer [refresh refresh-all clear]]
   [com.stuartsierra.component.user-helpers :refer [dev go reset set-dev-ns]]))

(tns.repl/set-refresh-dirs "dev" "src" "test")
(s/check-asserts true)
(stest/instrument)

(set-dev-ns 'why.datomic.dev)
