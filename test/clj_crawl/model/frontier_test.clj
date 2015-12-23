(ns clj-crawl.model.frontier-test
  (:use [clojure.test])
  (:require [clj-crawl.model.frontier :as f]))

(deftest ^:integration add-a-url
  (let [url "http://www.google.com"]
    (testing "We can insert a URL into the database"
      (is (f/insert url)
          :ok))
    (testing "We can retreive a URL from the database"
      (is (f/find-url url)
          {:url url
           :priority f/default-priority
           :depth f/default-depth}))))
