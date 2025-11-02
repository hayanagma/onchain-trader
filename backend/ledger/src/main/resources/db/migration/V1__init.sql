CREATE TABLE currencies (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(32) NOT NULL,
    name VARCHAR(64) NOT NULL,
    network VARCHAR(16) NOT NULL,
    decimals INT NOT NULL,
    kind VARCHAR(16) NOT NULL,
    contract_address VARCHAR(128),
    CONSTRAINT uq_currency_code_network UNIQUE (code, network)
);

CREATE TABLE network_accounts (
    id BIGSERIAL PRIMARY KEY,
    trader_id BIGINT NOT NULL,
    network VARCHAR(16) NOT NULL,
    CONSTRAINT uq_network_account_trader_network UNIQUE (trader_id, network)
);

CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(16) NOT NULL,
    address VARCHAR(128) NOT NULL,
    trader_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_wallet_address_network UNIQUE (address, network)
);

CREATE TABLE trader_currencies (
    id BIGSERIAL PRIMARY KEY,
    trader_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    CONSTRAINT uq_trader_currency UNIQUE (trader_id, currency_id),
    CONSTRAINT fk_trader_currencies_currency
        FOREIGN KEY (currency_id) REFERENCES currencies(id) ON DELETE CASCADE
);

CREATE TABLE wallet_nonces (
    id BIGSERIAL PRIMARY KEY,
    nonce VARCHAR(64) NOT NULL UNIQUE,
    wallet_address VARCHAR(128) NOT NULL,
    network VARCHAR(16) NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE payment_currencies (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(32) NOT NULL,
    name VARCHAR(64) NOT NULL,
    network VARCHAR(16) NOT NULL,
    decimals INT NOT NULL,
    kind VARCHAR(16) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    contract_address VARCHAR(128),
    CONSTRAINT uq_payment_currency_code_network UNIQUE (code, network)
);

CREATE TABLE subscription_payments (
    id BIGSERIAL PRIMARY KEY,
    trader_id BIGINT NOT NULL,
    plan VARCHAR(32) NOT NULL,
    amount NUMERIC(38, 8) NOT NULL,
    status VARCHAR(32) NOT NULL,
    payment_currency_code VARCHAR(32) NOT NULL,
    network VARCHAR(16) NOT NULL,
    deposit_address VARCHAR(128) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    transaction_hash VARCHAR(128),
    qr_code_url VARCHAR(255),
    auto_renewal BOOLEAN NOT NULL
);