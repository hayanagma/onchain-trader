package com.trader.ledger.verifier.wallet;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BitcoinWalletVerifier implements WalletVerifier {

    private final NetworkParameters network = MainNetParams.get();

    @Override
    public void validateAddress(String address) {
        try {
            Address.fromString(network, address);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Bitcoin wallet address");
        }
    }

    @Override
    public void validateSignature(String address, String nonce, String signature) {
        // TODO: implement real Bitcoin message signature verification
        boolean valid = true;
        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Bitcoin wallet signature");
        }
    }
}