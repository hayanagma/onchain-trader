package com.trader.ledger.validation;

import org.springframework.stereotype.Component;

import com.trader.shared.enums.PaymentNetworkType;

@Component
public class SubscriptionPaymentValidator {

    public void validateSubscriptionPayment(String currencyCode, PaymentNetworkType network) {
        validateCurrency(currencyCode);
        validateNetwork(network);
    }

    public String normalizeCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new IllegalArgumentException("Missing or invalid payment currency code");
        }
        return currencyCode.trim().toUpperCase();
    }

    private void validateCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new IllegalArgumentException("Missing or invalid payment currency code");
        }
    }

    private void validateNetwork(PaymentNetworkType network) {
        if (network == null) {
            throw new IllegalArgumentException("Missing or invalid network type");
        }
    }
}