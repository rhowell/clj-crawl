(ns clj-crawl.retreiver
  (:require [clj-crawl.model.frontier :as frontier]
            [cheshire.core :as cheshire]
            [environ.core :refer [env]]
            [clojurewerkz.urly.core :as u]
            [clojure.zip :as zip]
            [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [taoensso.timbre :as log]))

(declare process-page)
(declare process-failure)
(declare save-page)
(declare extract-links)

(defn download-page
  [url current-depth]
  (let [resp @(http/get url)]
    (if (:error resp)
      (process-failure url resp)
      (process-page url (:body resp) current-depth))))

(defn process-page
  "Processes a successfully retreived webpage"
  [url page current-depth]
  (let [links  (extract-links page)
        doc-id (save-page page links)]
    (when (pos? current-depth)
      (map #(frontier/add-url % (dec current-depth)) links))
    (frontier/record-success url doc-id)))

(defn process-failure
  "Process a failed page retreival, and see if we can recover"
  [url resp]
  (log/error (str "Failed to process " (:url resp) ": (" (:status resp) ") - " (:error resp)))
  (frontier/record-failure url (resp)))

(defn extract-links
  "Finds all links in a page"
  [url page]
  (let [anchors (-> page
                    html/html-snippet
                    (html/select [:a]))
        links   (map (fn [a] (:href (:attrs a))) anchors)]
    (->> links
         (remove #(or (nil? %) (empty? %))) ; Need to get smarter about what links we filter out
         (mapv (fn [link] (u/resolve url link)))))) ; urly resolve makes a url absolute, based on the host & protocol of the first param

(defn save-page
  "Saves this page to ElasticSearch.  Returns the doc _id, nil if we failed to index"
  [url page out-links]
  (let [resp @(http/post (str (env :elasticsearch-db) "/docs") ; TODO Don't hardcode this business
                         (cheshire/parse-string {:page page
                                                 :out_links out-links
                                                 :in_links nil}))]
    (if (= (:status resp) 201)
      (:_id (cheshire/parse-string (:body resp) true))
      (log/error (str "Failed to index " url ": " resp)))))

(defn normalize-text
  "Pulls all the text from the body.  We'll index it in ES for search
   Based on http://josf.info/blog/2014/10/02/practical-zippers-extracting-text-with-enlive/"
  [text]
  (let [body (html/select (->> text
                           clojure.string/lower-case
                           java.io.StringReader.
                           html/html-resource)
                    [:body])]
    (->> body
         first
         zip/xml-zip
         (iterate zip/next)
         (take-while (complement zip/end?))
         (filter (complement zip/branch?))
         (map zip/node)
         (map (fn [node] (str (clojure.string/trim node) " ")))
         (apply str))))
