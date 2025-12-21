CREATE TABLE outbox (
    id BIGINT AUTO_INCREMENT NOT NULL,
    event_id VARCHAR(100) NOT NULL,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id VARCHAR(50) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload JSON NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP NULL,

    CONSTRAINT pk_outbox_id PRIMARY KEY (id),
    CONSTRAINT uk_outbox_event_id UNIQUE (event_id)
);

CREATE INDEX idx_outbox_published_at_id
ON outbox (published_at, id);
