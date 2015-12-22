(ns clj-crawl.app)

(declare print-help)

(defn -main
  "Main entry point"
  ([]
   (print-help))
  ([cmd & args]
   (condp = cmd
     "help" (if args
              (print-help args)
              (print-help))
     "start" (println "Not yet implemented")
     "enqueue" (println "Not yet implemented")
     "status" (println "Not yet implemented"))))


(defn print-help
  "Prints either a general help message or detailed help on a given command"
  ([]
   (println "start\t\tRuns the crawler")
   (println "enqueue\t\tAdds a URL to the crawl frontier")
   (println "status\t\tPrints the status of the crawler with basic statistics")
   (println "stop\t\tStops the crawl")
   )
  ([cmd]
   (println "Not yet implemented")
   ))
