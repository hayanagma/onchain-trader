CREATE TABLE trade_requests (
    id BIGSERIAL PRIMARY KEY,
    trader_id BIGINT NOT NULL,
    wallet_address VARCHAR(64) NOT NULL,
    token_in VARCHAR(64) NOT NULL,
    token_out VARCHAR(64) NOT NULL,
    amount_in NUMERIC(38,18) NOT NULL,
    min_amount_out NUMERIC(38,18) NOT NULL,
    status VARCHAR(16) NOT NULL,
    created_at BIGINT NOT NULL,
    executed_at BIGINT,
    tx_hash VARCHAR(128)
);

CREATE TABLE trade_executions (
    id BIGSERIAL PRIMARY KEY,
    trade_request_id BIGINT NOT NULL,
    executed_at BIGINT NOT NULL,
    tx_hash VARCHAR(128),
    executed_amount NUMERIC(38,18),
    received_amount NUMERIC(38,18),
    profit_loss NUMERIC(38,18),
    CONSTRAINT fk_trade_execution_request FOREIGN KEY (trade_request_id)
        REFERENCES trade_requests(id) ON DELETE CASCADE
);

CREATE TABLE sequences (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(32) NOT NULL,
    name VARCHAR(128) NOT NULL,
    created_at BIGINT NOT NULL,
    auto_exit BOOLEAN NOT NULL,
    exit_leverage NUMERIC(38,18),
    CONSTRAINT uq_sequence_name_network UNIQUE (name, network)
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    strategy_type VARCHAR(16) NOT NULL,
    user_address VARCHAR(64) NOT NULL,
    token_in VARCHAR(64) NOT NULL,
    token_out VARCHAR(64) NOT NULL,
    amount_in NUMERIC(38,18) NOT NULL,
    min_amount_out NUMERIC(38,18) NOT NULL,
    price_target NUMERIC(38,18),
    interval_seconds BIGINT,
    expiry BIGINT NOT NULL,
    nonce BIGINT NOT NULL,
    signature VARCHAR(132) NOT NULL,
    sequence_id BIGINT,
    CONSTRAINT fk_order_sequence FOREIGN KEY (sequence_id) REFERENCES sequences(id) ON DELETE CASCADE
);

CREATE TABLE sequence_executions (
    id BIGSERIAL PRIMARY KEY,
    sequence_id BIGINT NOT NULL,
    sell_all BOOLEAN NOT NULL,
    executed_at BIGINT NOT NULL,
    tx_hash VARCHAR(128),
    CONSTRAINT fk_execution_sequence FOREIGN KEY (sequence_id) REFERENCES sequences(id) ON DELETE CASCADE
);

CREATE TABLE execution_wallets (
    id BIGSERIAL PRIMARY KEY,
    execution_id BIGINT NOT NULL,
    wallet_address VARCHAR(64) NOT NULL,
    CONSTRAINT fk_wallet_execution FOREIGN KEY (execution_id) REFERENCES sequence_executions(id) ON DELETE CASCADE
);