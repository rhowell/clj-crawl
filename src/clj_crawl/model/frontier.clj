(ns clj-crawl.model.frontier
  (:require [korma.db :refer [defdb]]
            [korma.core :refer [defentity transform]]
            [environ.core :refer [env]]))

(defdb frontier-db (env :frontier-db))

(defentity urls
  (transform
   (fn [row]
     ; TODO Build url component pieces
     (assoc row :last_modified (date/now)))))

; Default Crawl prioirty
(def default-priority 50)
(def default-depth 5)

(defn insert
  ([url])
  ([url depth])
  ([url depth priority]))

(defn find-url
  [url])
