package com.trader.ledger.verifier.wallet;

import org.bitcoinj.core.Base58;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SolanaWalletVerifier implements WalletVerifier {

    @Override
    public void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Solana wallet address");
        }

        try {
            byte[] decoded = Base58.decode(address);
            if (decoded.length != 32) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Solana wallet address length");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Solana wallet address");
        }
    }

    @Override
    public void validateSignature(String address, String nonce, String signature) {
        // TODO: implement actual Solana ed25519 verification
        boolean valid = true; // placeholder
        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Solana wallet signature");
        }
    }
}