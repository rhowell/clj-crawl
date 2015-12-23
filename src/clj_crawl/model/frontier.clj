(ns clj-crawl.model.frontier
  (:require [korma.db :refer [defdb]]
            [korma.core :refer [defentity transform insert values select where]]
            [environ.core :refer [env]]
            [clojurewerkz.urly.core :as u]
            [clj-time.local :as time]))

(println "Frontier DB: " (env :frontier-db))
(defdb frontier-db (env :frontier-db))

(defentity urls
  (transform
   (fn [row]
     (assoc row :last_modified (time/local-now)))))

                                        ; Default Crawl prioirty
(def default-priority 50)
(def default-depth 5)
(declare get-url-components)

(defn add-url
  "Adds a URL to the crawl frontier.  It will reject anything not considered unique (subdomin, domain, and path currently)"
  ([url]
   (add-url url default-depth default-priority))
  ([url depth]
   (add-url url depth default-priority))
  ([url depth priority]
   (insert urls
           (values (merge {:url url
                           :priority priority
                           :crawl_depth depth}
                          (get-url-components url))))))

(defn get-url-components
  "Breaks a URL into it's component pieces.
   URL regex adapted from https://gist.github.com/dperini/729294"
  [url]
  (-> url
      u/url-like
      u/as-map
      (clojure.set/rename-keys {:protocol :url_protocol
                                :host :url_hostname
                                :user-info :url_auth
                                :port :url_port
                                :path :url_path
                                :query :url_params
                                :fragment :url_fragment})
      (select-keys [:url_protocol :url_auth :url_hostname :url_port :url_path :url_params :url_fragment])))

(defn find-url
  "Finds a (very) specific url in the frontier.  The URL must match exactly"
  [url]
  (select urls
          (where {:url url})))
