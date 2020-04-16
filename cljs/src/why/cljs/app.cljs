(ns why.cljs.app
  (:require
   ["apollo-cache-inmemory" :refer [InMemoryCache]]
   ["apollo-client" :refer [ApolloClient]]
   ["apollo-link" :as apollo.link]
   ["apollo-link-http" :refer [HttpLink]]
   ["apollo-link-ws" :refer [WebSocketLink]]
   ["apollo-utilities" :as apollo.util]
   [clojure.pprint :refer [pprint]]
   [goog.dom :as gdom]
   [rum.core :as rum]))

;; Gonna regret having "cljs" in the path and extension.

(enable-console-print!)

(defonce app-state
  (atom {::version 0}))

(rum/defc Router < rum/reactive
  [app-state]
  (let [client (rum/react app-state)]
    (rum/fragment
     [:h1 "Why cljs?"]
     [:code [:pre (pprint (js->clj client))]])))

(defn- link-splitter
  [{:keys [query]}]
  (let [{kind :kind op :operation}
        (apollo.util/getMainDefinition query)]
    (and (= "OperationDefinition" kind)
         (= "subscription" op))))

(defn ^:export init
  []
  (let [http-link (HttpLink. #js {:uri "http://localhost:8080/v1/graphql"})
        ws-link   (WebSocketLink. #js {:uri     "ws://localhost:8080/v1/graphql"
                                       :options {:reconnect true}})
        link      (apollo.link/split link-splitter ws-link http-link)
        client    (ApolloClient. #js {:link link :cache (InMemoryCache.)})]
    (reset! app-state {:apollo/client client})
    (rum/mount (Router app-state) (gdom/getElement "root"))
    (println "We're cooking!")))

(defn ^:export reload
  []
  (println "Something will need to be done.")
  (swap! app-state update :version inc))
