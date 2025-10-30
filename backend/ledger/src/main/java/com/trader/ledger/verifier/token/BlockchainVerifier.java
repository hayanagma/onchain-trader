package com.trader.ledger.verifier.token;

import com.trader.ledger.dto.TokenMetadata;

public interface BlockchainVerifier {
    TokenMetadata verify(String contractAddress);
}