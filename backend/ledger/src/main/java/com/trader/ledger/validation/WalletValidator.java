package com.trader.ledger.validation;

import org.springframework.stereotype.Component;

import com.trader.ledger.verifier.wallet.WalletVerifier;
import com.trader.ledger.verifier.wallet.WalletVerifierFactory;
import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;
import com.trader.shared.enums.NetworkType;

@Component
public class WalletValidator {

    private final WalletVerifierFactory walletVerifierFactory;

    public WalletValidator(WalletVerifierFactory walletVerifierFactory) {
        this.walletVerifierFactory = walletVerifierFactory;
    }

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

        WalletVerifier verifier = walletVerifierFactory.getVerifier(network);
        String normalized = normalizeAddress(address);
        verifier.validateAddress(normalized);

        if (checkSignature) {
            verifier.validateSignature(normalized, nonce, signature);
        }

        return new WalletValidationResponse(normalized, network);
    }

    private String normalizeAddress(String address) {
        return address == null ? null : address.trim();
    }
}