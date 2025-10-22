CREATE TABLE currencies (
    code VARCHAR(32) PRIMARY KEY,
    network VARCHAR(16) NOT NULL,
    decimals INT NOT NULL,
    enabled BOOLEAN NOT NULL,
    kind VARCHAR(16) NOT NULL,
    contract_address VARCHAR(128)
);

CREATE TABLE bankrolls (
    id BIGSERIAL PRIMARY KEY,
    currency_code VARCHAR(32) NOT NULL UNIQUE,
    amount NUMERIC(38, 18) NOT NULL DEFAULT 0,
    CONSTRAINT fk_bankroll_currency FOREIGN KEY (currency_code) REFERENCES currencies(code)
);

CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(16) NOT NULL,
    address VARCHAR(128) NOT NULL,
    player_id BIGINT NOT NULL,
    CONSTRAINT uq_wallet_address_network UNIQUE (address, network)
);

CREATE TABLE player_balances (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(38, 18) NOT NULL DEFAULT 0,
    wallet_id BIGINT NOT NULL,
    currency_code VARCHAR(32) NOT NULL,
    reserved_amount NUMERIC(38, 18) NOT NULL DEFAULT 0,
    CONSTRAINT fk_balance_wallet FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE,
    CONSTRAINT uq_balance_wallet_currency UNIQUE (wallet_id, currency_code)
);
