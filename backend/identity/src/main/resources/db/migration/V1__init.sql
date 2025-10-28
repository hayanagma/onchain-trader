CREATE TABLE players (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    banned_reason VARCHAR(255),
    token_version INT NOT NULL DEFAULT 1,
    last_username_change_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    subscribed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE admins (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    token_version INT NOT NULL DEFAULT 1
);