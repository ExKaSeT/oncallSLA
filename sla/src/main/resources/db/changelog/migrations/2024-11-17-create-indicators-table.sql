-- Liquibase formatted SQL
-- changeset admin:2024-11-17-create-indicators-table
CREATE TABLE indicators
(
    name       VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    slo DOUBLE NOT NULL,
    value DOUBLE NOT NULL,
    is_bad     BOOLEAN      NOT NULL,
    PRIMARY KEY (name, created_at)
);

CREATE INDEX idx_indicators_name ON indicators (name);
CREATE INDEX idx_indicators_created_at ON indicators (created_at);