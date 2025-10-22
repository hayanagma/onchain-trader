package com.casino.ledger.validation;

import org.bitcoinj.core.Base58;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.casino.ledger.dto.WalletSignatureValidationRequest;
import com.casino.ledger.dto.WalletValidationRequest;
import com.casino.ledger.dto.WalletValidationResponse;

@Component
public class WalletValidator {

    public WalletValidationResponse validate(WalletValidationRequest request) {
        return doValidation(request.getAddress(), request.getNetwork(), null, null, false);
    }

    public WalletValidationResponse validateSignature(WalletSignatureValidationRequest request) {
        return doValidation(request.getAddress(), request.getNetwork(), request.getNonce(), request.getSignature(),
                true);
    }

    private WalletValidationResponse doValidation(
            String address,
            String network,
            String nonce,
            String signature,
            boolean checkSignature) {
        String normalizedAddress = normalizeAddress(address);
        String normalizedNetwork = normalizeNetwork(network);

        validateNetwork(normalizedNetwork);
        validateTronWallet(normalizedAddress);

        if (checkSignature) {
            validateSignature(normalizedAddress, normalizedNetwork, nonce, signature);
        }

        return new WalletValidationResponse(normalizedAddress, normalizedNetwork);
    }

    public void validateNetwork(String network) {
        if (!"TRON".equalsIgnoreCase(network)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported network: " + network);
        }
    }

    public void validateTronWallet(String address) {
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

    public void validateSignature(String walletAddress, String network, String message, String signature) {
        if (!"TRON".equalsIgnoreCase(network)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported network: " + network);
        }

        // TODO: implement actual blockchain Tron signature validation
        boolean valid = true; // placeholder

        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid wallet signature");
        }
    }

    public String normalizeAddress(String address) {
        return address == null ? null : address.trim();
    }

    public String normalizeNetwork(String network) {
        return network == null ? null : network.trim().toUpperCase();
    }
}
