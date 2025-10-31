package com.trader.shared.dto.identity.subscription;

import com.trader.shared.enums.SubscriptionPlan;

import jakarta.validation.constraints.NotNull;

public class SubscriptionRequest {
    @NotNull
    private SubscriptionPlan plan;
    private boolean autoRenewal;

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public boolean isAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }
}