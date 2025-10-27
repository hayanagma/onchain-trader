package com.trader.ledger.verifier;

import com.trader.shared.enums.NetworkType;
import org.springframework.stereotype.Component;

@Component
public class BlockchainVerifierFactory {

    private final TronTokenVerifier tronTokenVerifier;

    public BlockchainVerifierFactory(TronTokenVerifier tronTokenVerifier) {
        this.tronTokenVerifier = tronTokenVerifier;
    }

    public BlockchainVerifier getVerifier(NetworkType network) {
        return switch (network) {
            case TRON -> tronTokenVerifier;
            default -> throw new UnsupportedOperationException("Unsupported network: " + network);
        };
    }
}