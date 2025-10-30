package com.trader.ledger.verifier.wallet;

public interface WalletVerifier {
    void validateAddress(String address);
    void validateSignature(String address, String nonce, String signature);
}