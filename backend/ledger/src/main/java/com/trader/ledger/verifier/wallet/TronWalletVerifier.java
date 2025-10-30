package com.trader.ledger.verifier.wallet;

import org.bitcoinj.core.Base58;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TronWalletVerifier implements WalletVerifier {

    @Override
    public void validateAddress(String address) {
        if (address == null || !address.startsWith("T")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid TRON wallet address");
        }
        try {
            byte[] decoded = Base58.decodeChecked(address);
            if (decoded.length != 21 || (decoded[0] & 0xFF) != 0x41) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid TRON wallet address");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid TRON wallet address");
        }
    }

    @Override
    public void validateSignature(String address, String nonce, String signature) {
        // placeholder for real signature verification
        boolean valid = true;
        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid TRON signature");
        }
    }
}