
CREATE TABLE IF NOT EXISTS urls (
       id               SERIAL PRIMARY KEY,
       url              TEXT NOT NULL,
       priority         INT NOT NULL,
       crawl_depth      INT NOT NULL,
       url_protocol     TEXT,
       url_auth         TEXT,
       url_hostname     TEXT, -- subdomain, domain, and TLD
       url_port         INT,
       url_path         TEXT,
       url_params       TEXT,
       url_fragment     TEXT,
       created_at       TIMESTAMP NOT NULL default now(),
       last_modified    TIMESTAMP NOT NULL default now(),
       last_crawled_at  TIMESTAMP
);
CREATE INDEX urls_url_hostname ON urls(url_hostname);
-- The same URL with a different fragement, protocol, etc... should be treated the same
CREATE UNIQUE INDEX urls_uniqueness ON urls(url_hostname, url_path, url_params);

