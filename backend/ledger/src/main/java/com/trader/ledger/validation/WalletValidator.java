package com.trader.ledger.validation;

import org.bitcoinj.core.Base58;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;
import com.trader.shared.enums.NetworkType;

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
            NetworkType network,
            String nonce,
            String signature,
            boolean checkSignature) {
        String normalizedAddress = normalizeAddress(address);

        validateNetwork(network);
        validateTronWallet(normalizedAddress);

        if (checkSignature) {
            validateSignature(normalizedAddress, network, nonce, signature);
        }

        return new WalletValidationResponse(normalizedAddress, network);
    }

    public void validateNetwork(NetworkType network) {
        if (network != NetworkType.TRON) {
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

    public void validateSignature(String walletAddress, NetworkType network, String message, String signature) {
        if (network != NetworkType.TRON) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported network: " + network);
        }

        // TODO: implement actual Tron signature verification
        boolean valid = true; // placeholder

        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid wallet signature");
        }
    }

    public String normalizeAddress(String address) {
        return address == null ? null : address.trim();
    }

}
