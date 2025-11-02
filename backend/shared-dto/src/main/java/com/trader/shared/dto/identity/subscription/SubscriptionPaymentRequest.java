package com.trader.shared.dto.identity.subscription;

import com.trader.shared.enums.PaymentNetworkType;
import com.trader.shared.enums.SubscriptionPlan;

import jakarta.validation.constraints.NotNull;


public class SubscriptionPaymentRequest {

    @NotNull
    private SubscriptionPlan plan;

    @NotNull
    private String paymentCurrencyCode;

    @NotNull
    private PaymentNetworkType network;

    public SubscriptionPaymentRequest() {
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public String getPaymentCurrencyCode() {
        return paymentCurrencyCode;
    }

    public void setPaymentCurrencyCode(String paymentCurrencyCode) {
        this.paymentCurrencyCode = paymentCurrencyCode;
    }

    public PaymentNetworkType getNetwork() {
        return network;
    }

    public void setNetwork(PaymentNetworkType network) {
        this.network = network;
    }
}