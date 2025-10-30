package com.trader.ledger.verifier.token;

import com.trader.shared.enums.NetworkType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BlockchainVerifierFactory {

    private final TronTokenVerifier tronTokenVerifier;
    private final SolanaTokenVerifier solanaTokenVerifier;

    public BlockchainVerifierFactory(TronTokenVerifier tronTokenVerifier, SolanaTokenVerifier solanaTokenVerifier) {
        this.tronTokenVerifier = tronTokenVerifier;
        this.solanaTokenVerifier = solanaTokenVerifier;
    }

    public BlockchainVerifier getVerifier(NetworkType network) {
        return switch (network) {
            case TRON -> tronTokenVerifier;
            case SOLANA -> solanaTokenVerifier;
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported network: " + network);
        };
    }
}