package com.trader.ledger.verifier.wallet;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;
import org.springframework.http.HttpStatus;

@Service
public class EthereumWalletVerifier implements WalletVerifier {

    @Override
    public void validateAddress(String address) {
        if (address == null || !address.startsWith("0x")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Ethereum wallet address");
        }
        try {
            String clean = Numeric.cleanHexPrefix(address);
            if (clean.length() != 40) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Ethereum address length");
            }
            Keys.toChecksumAddress(address);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Ethereum wallet address");
        }
    }

    @Override
    public void validateSignature(String address, String nonce, String signature) {
        // TODO: implement real ECDSA (secp256k1) recovery verification
        boolean valid = true;
        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Ethereum wallet signature");
        }
    }
}