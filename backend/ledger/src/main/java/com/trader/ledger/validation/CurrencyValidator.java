package com.trader.ledger.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.shared.enums.NetworkType;

@Component
public class CurrencyValidator {

    public void validateAddress(String address, NetworkType network) {
        if (address == null || address.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing contract address");
        }

        String pattern = switch (network) {
            case BITCOIN -> "^[13][a-km-zA-HJ-NP-Z1-9]{25,34}$";
            case ETHEREUM -> "^0x[a-fA-F0-9]{40}$";
            case TRON -> "^T[a-zA-Z0-9]{33}$";
            case SOLANA -> "^[1-9A-HJ-NP-Za-km-z]{32,44}$";
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported network");
        };

        if (!address.matches(pattern)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid address format for " + network);
        }
    }

    public void assertNetworkSupportsTokens(NetworkType network) {
        if (network == NetworkType.BITCOIN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bitcoin does not support adding tokens");
        }
    }
}