CREATE TABLE traders (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    banned_reason VARCHAR(255),
    token_version INT NOT NULL DEFAULT 1,
    last_username_change_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    subscription_plan VARCHAR(20) NOT NULL DEFAULT 'FREE'
);

CREATE TABLE admins (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL,
    token_version INT NOT NULL DEFAULT 1
);
CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    trader_id BIGINT NOT NULL UNIQUE REFERENCES traders(id) ON DELETE CASCADE,
    plan VARCHAR(50) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);