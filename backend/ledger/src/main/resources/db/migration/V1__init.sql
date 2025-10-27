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

CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(16) NOT NULL,
    address VARCHAR(128) NOT NULL,
    player_id BIGINT NOT NULL,
    CONSTRAINT uq_wallet_address_network UNIQUE (address, network)
);

CREATE TABLE player_currencies (
    id BIGSERIAL PRIMARY KEY,
    player_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    CONSTRAINT uq_player_currency UNIQUE (player_id, currency_id),
    CONSTRAINT fk_player_currencies_currency
        FOREIGN KEY (currency_id) REFERENCES currencies(id)
);