package com.trader.ledger.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.WalletNonce;
import com.trader.shared.enums.NetworkType;

@Component
public class WalletNonceValidator {

    public void validate(WalletNonce entity, String walletAddress, NetworkType network) {
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