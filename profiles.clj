{:dev {:env {:frontier-db {:classname "org.postgresql.Driver"
                           :subprotocol "postgresql"
                           :subname "//127.0.0.1:5432/clj-crawl-dev"
                           :user "clj-crawler"
                           :password "purplealigators"}}}
 :test {:env {:frontier-db {:classname "org.postgresql.Driver"
                            :subprotocol "postgresql"
                            :subname "//127.0.0.1:5432/clj-crawl-test"
                            :user "clj-crawler"
                            :password "purplealigators"}}}}
