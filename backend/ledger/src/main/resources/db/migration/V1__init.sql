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
    CONSTRAINT uq_wallet_address_network UNIQUE (address, network),
    CONSTRAINT fk_wallet_trader FOREIGN KEY (trader_id)
        REFERENCES traders(id) ON DELETE CASCADE
);

CREATE TABLE trader_currencies (
    id BIGSERIAL PRIMARY KEY,
    network_account_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    CONSTRAINT uq_network_account_currency UNIQUE (network_account_id, currency_id),
    CONSTRAINT fk_trader_currencies_currency
        FOREIGN KEY (currency_id) REFERENCES currencies(id) ON DELETE CASCADE,
    CONSTRAINT fk_trader_currencies_network_account
        FOREIGN KEY (network_account_id) REFERENCES network_accounts(id) ON DELETE CASCADE
);

CREATE TABLE wallet_nonces (
    id BIGSERIAL PRIMARY KEY,
    nonce VARCHAR(64) NOT NULL UNIQUE,
    wallet_address VARCHAR(128) NOT NULL,
    network VARCHAR(16) NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);