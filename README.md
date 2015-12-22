# clj-crawl

A Web Crawler written in Clojure

## Overview

Our web crawling framework consist of the following pieces:

Crawl Frontier:
    Decides what pages should be crawled next.  This involves cycle and spider trap detection, depth limits, etc...

Retreiver:
    Reponsible for retreiving the actual page.

Indexer:
    Indexes each page, extracts links, and builds a collection of inlinks (pages that link to this one).

Ranker:
    Determines the relative value of this page.

## Usage

The frontier must first be populated with a URL and a crawl depth.  We only crawl URLs to a given depth to prevent infinite loops.  Depth denotes the number of links we will take from a given page.  For example, if we crawl a page with a depth of 1, we will follow every link on a page, with each of those links having a depth of 0.  Those links will be crawled, but their links will not be followed.  This is the same concept as limiting a tree-search to N levels.

## Pre-reqs

PostgreSQL 9.4+ - The crawl frontier keeps the list of to be crawled pages in Postgres
Cassandra 2+ - All pages are indexed into Cassandra along with related meta-data
Java 8+ JRE - For Clojure

## License

Copyright © 2015 Ronnie Howell

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
