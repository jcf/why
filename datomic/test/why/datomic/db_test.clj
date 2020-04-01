(ns why.datomic.db-test
  (:require
   [clojure.test :refer [deftest is]]
   [why.datomic.db :as sut]
   [why.datomic.test.util :as t]))

(deftest idents
  (t/with-system [{:keys [datomic]} (t/system)]
    (let [xs (sut/idents (sut/db datomic))]
      (is (every? (comp some? namespace) xs))
      (is (contains? xs :db/doc)))))
