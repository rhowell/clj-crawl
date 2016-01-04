(ns clj-crawl.model.frontier
  (:require [korma.db :refer [defdb]]
            [korma.core :refer [defentity transform insert values select where delete]]
            [environ.core :refer [env]]
            [clojurewerkz.urly.core :as u]
            [clj-time.local :as time]))

(defdb frontier-db (env :frontier-db))
(defentity urls)

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
   (try
     (do
       (insert urls
               (values (merge {:url url
                               :priority priority
                               :crawl_depth depth}
                              (get-url-components url))))
       :ok)
     (catch org.postgresql.util.PSQLException e
       (do
         (.println *err* (str "Failed to insert into frontier database: " e))
         :error)))))

(defn find-url
  "Finds a (very) specific url in the frontier.  The URL must match exactly"
  [url]
  (first (select urls (where {:url url}))))

(defn drop-url
  [url]
  (delete urls (where {:url url})))

(defn get-url-components
  "Breaks a URL into it's component pieces.
   URL regex adapted from https://gist.github.com/dperini/729294"
  [url]
  (let [params (-> url
                   u/url-like
                   u/as-map
                   (clojure.set/rename-keys {:protocol :url_protocol
                                             :host :url_hostname
                                             :user-info :url_auth
                                             :port :url_port
                                             :path :url_path
                                             :query :url_params
                                             :fragment :url_fragment})
                   (select-keys [:url_protocol :url_auth :url_hostname :url_port :url_path :url_params :url_fragment]))]
    ; If a section is nil, don't bother returning it
    (into {} (remove #(nil? (val %)) params))))

(defn record-success
  [url doc-id]
  )

(defn record-failure
  [url resp]
  )
