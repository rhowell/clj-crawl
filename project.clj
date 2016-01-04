(defproject clj-crawl "0.1.0-SNAPSHOT"
  :description "A relatively simple web crawler written in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.3.1"] ; Lifecycle management
                 [enlive "1.1.6"]
                 [clj-time "0.5.1"]
                 [korma "0.4.2"] ; SQL Library
                 [postgresql "9.3-1102.jdbc41"]
                 [cheshire "5.5.0"] ; JSON parser/generator
                 [com.taoensso/timbre "4.1.4"] ; Much nicer logging
                 [clojurewerkz/urly "1.0.0"] ; For analyzing URLs
                 [environ "1.0.1"] ; Environment Variables
                 [http-kit "2.1.19"]]
  :plugins [[lein-environ "1.0.1"]]
  :main clj-crawl.app
  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :all (constantly true)}
  :mirrors {#"clojars" {:name "Clojars Mirror"
                        :url "https://clojars-mirror.tcrawley.org/repo/"}}
  :profiles  {:uberjar {:aot :all
                        :main clj-crawl.app}})
