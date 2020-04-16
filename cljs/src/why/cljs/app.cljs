(ns why.cljs.app)

;; Gonna regret having "cljs" in the path and extension.

(enable-console-print!)

(defn ^:export init
  []
  (println "We're cooking!"))

(defn ^:export reload
  []
  (println "Something will need to be done."))
