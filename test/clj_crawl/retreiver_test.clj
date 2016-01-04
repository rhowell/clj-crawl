(ns clj-crawl.retreiver-test
  (:use [clojure.test])
  (:require [clj-crawl.retreiver :as retreiver]))

(deftest ^:integration download-page-test
  )

(deftest process-page-test
  )

(deftest process-failure-test
  )

(deftest extract-links-test
  (testing "We can pull all the links from a page"
    (let [page (slurp "./test/fixtures/google.html")]
      (is (= (retreiver/extract-links "https://www.google.com" page)
             ["https://www.google.com/imghp?hl=en&tab=wi"
              "https://maps.google.com/maps?hl=en&tab=wl"
              "https://play.google.com/?hl=en&tab=w8"
              "https://www.youtube.com/?tab=w1"
              "https://news.google.com/nwshp?hl=en&tab=wn"
              "https://mail.google.com/mail/?tab=wm"
              "https://drive.google.com/?tab=wo"
              "https://www.google.com/intl/en/options/"
              "http://www.google.com/history/optout?hl=en"
              "https://www.google.com/preferences?hl=en"
              "https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=https://www.google.com/"
              "https://www.google.com/chrome/browser/?hl=en&brand=CHNG&utm_source=en-hpp&utm_medium=hpp&utm_campaign=en"
              "https://www.google.com/advanced_search?hl=en&authuser=0"
              "https://www.google.com/language_tools?hl=en&authuser=0"
              "https://www.google.com/url?q=https://play.google.com/store/apps/collection/promotion_3001d1a_google_apps&source=hpp&id=5083181&ct=8&usg=AFQjCNH2g_4hADNfzHkowt7_z-UAPxs6cg"
              "https://www.google.com/url?q=https://itunes.apple.com/developer/google-inc./id281956209&source=hpp&id=5083181&ct=8&usg=AFQjCNGvJX8SheQV4HAD9AwyqapxpHWdiw"
              "https://www.google.com/intl/en/ads/"
              "https://www.google.com/services/"
              "https://plus.google.com/116899029375914044550"
              "https://www.google.com/intl/en/about.html"
              "https://www.google.com/intl/en/policies/privacy/"
              "https://www.google.com/intl/en/policies/terms/"])))))

(deftest ^:integration save-page-test
  )
