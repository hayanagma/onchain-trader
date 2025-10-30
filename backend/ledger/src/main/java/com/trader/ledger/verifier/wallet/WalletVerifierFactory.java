package com.trader.ledger.verifier.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.shared.enums.NetworkType;

@Component
public class WalletVerifierFactory {

    private final TronWalletVerifier tronVerifier;
    private final SolanaWalletVerifier solanaVerifier;
    private final EthereumWalletVerifier ethereumVerifier;
    private final BitcoinWalletVerifier bitcoinVerifier;

    public WalletVerifierFactory(
            TronWalletVerifier tronVerifier,
            SolanaWalletVerifier solanaVerifier,
            EthereumWalletVerifier ethereumVerifier,
            BitcoinWalletVerifier bitcoinVerifier) {
        this.tronVerifier = tronVerifier;
        this.solanaVerifier = solanaVerifier;
        this.ethereumVerifier = ethereumVerifier;
        this.bitcoinVerifier = bitcoinVerifier;
    }

    public WalletVerifier getVerifier(NetworkType network) {
        return switch (network) {
            case TRON -> tronVerifier;
            case SOLANA -> solanaVerifier;
            case ETHEREUM -> ethereumVerifier;
            case BITCOIN -> bitcoinVerifier;
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported network: " + network);
        };
    }
}