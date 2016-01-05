(ns clj-crawl.retreiver-test
  (:use [clojure.test])
  (:require [clj-crawl.retreiver :as retreiver]))

(deftest ^:integration download-page-test
  )

(deftest process-page-test
  )

(deftest process-failure-test
  )


(deftest normalize-text-test
  (testing "We can pull out all plain text from a page"
    (let [page (slurp "./test/fixtures/nokogiri.html")]
      (is (= (retreiver/normalize-text page)
             "     nokogiri 鋸        docs  github    installation  tutorials  getting help               tutorials    installing nokogiri  parsing an html/xml document  searching a xml/html document  modifying an html/xml document  ensuring well-formed markup  getting help  more resources              contribute to these docs at sparklemotion/nokogiri.org-tutorials .  powered by octopress . theme is oscailte .           ")))))

(deftest extract-links-test
  (testing "We can pull all the links from a page"
    (let [page (slurp "./test/fixtures/nokogiri.html")]
      (is (= (retreiver/extract-links "http://www.nokogiri.org" page)
             ["http://www.nokogiri.org/"
              "http://rdoc.info/github/sparklemotion/nokogiri"
              "https://github.com/sparklemotion/nokogiri"
              "http://www.nokogiri.org/tutorials/installing_nokogiri.html"
              "http://www.nokogiri.org/tutorials/"
              "http://www.nokogiri.org/tutorials/getting_help.html"
              "http://www.nokogiri.org/tutorials/installing_nokogiri.html"
              "http://www.nokogiri.org/tutorials/parsing_an_html_xml_document.html"
              "http://www.nokogiri.org/tutorials/searching_a_xml_html_document.html"
              "http://www.nokogiri.org/tutorials/modifying_an_html_xml_document.html"
              "http://www.nokogiri.org/tutorials/ensuring_well_formed_markup.html"
              "http://www.nokogiri.org/tutorials/getting_help.html"
              "http://www.nokogiri.org/tutorials/more_resources.html"
              "http://github.com/sparklemotion/nokogiri.org-tutorials"
              "http://octopress.org/"
              "http://github.com/coogie/oscailte"])))))

(deftest ^:integration save-page-test
  )
