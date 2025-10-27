CREATE TABLE currencies (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(32) UNIQUE NOT NULL,
    network VARCHAR(16) NOT NULL,
    decimals INT NOT NULL,
    kind VARCHAR(16) NOT NULL,
    contract_address VARCHAR(128),
    player_id BIGINT
);

CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(16) NOT NULL,
    address VARCHAR(128) NOT NULL,
    player_id BIGINT NOT NULL,
    CONSTRAINT uq_wallet_address_network UNIQUE (address, network)
);