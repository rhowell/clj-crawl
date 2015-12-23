(ns clj-crawl.model.frontier-test
  (:use [clojure.test])
  (:require [clj-crawl.model.frontier :as f]))

(deftest ^:integration url-lifecycle
  (let [url "http://www.google.com"]
    (testing "We can insert a URL into the database"
      (is (= (f/add-url url) :ok)))
    (testing "We can retreive a URL from the database"
      (is (= (select-keys (f/find-url url) [:url :priority :crawl_depth])
             {:url url
              :priority f/default-priority
              :crawl_depth f/default-depth})))
    (testing "We can remove URLs from the database"
      (is (= (f/drop-url url) 1)))))

(deftest get-url-component-tests
  (testing "We can break a url into its composit pieces"
    (is (= (f/get-url-components "https://www.google.com")
           {:url_protocol "https"
            :url_hostname "www.google.com"
            :url_port -1
            :url_path "/"}))
    (is (= (f/get-url-components "http://example.com/some/embedded/resource?a=10&b=20#fancy_fragment")
           {:url_protocol "http"
            :url_hostname "example.com"
            :url_port -1
            :url_path "/some/embedded/resource"
            :url_params "a=10&b=20"
            :url_fragment "fancy_fragment"}))))

