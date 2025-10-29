package com.trader.auth.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.model.TraderNonce;
import com.trader.shared.enums.NetworkType;

@Component
public class NonceValidator {

    public void validate(TraderNonce entity, String walletAddress, NetworkType network) {
        if (entity.isUsed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nonce already used");
        }
        if (!entity.getWalletAddress().equals(walletAddress)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet address mismatch");
        }
        if (!entity.getNetwork().equals(network)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Network mismatch");
        }
    }
}