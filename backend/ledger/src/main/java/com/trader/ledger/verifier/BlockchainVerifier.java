package com.trader.ledger.verifier;

import com.trader.ledger.dto.TokenMetadata;

public interface BlockchainVerifier {
    TokenMetadata verify(String contractAddress);
}