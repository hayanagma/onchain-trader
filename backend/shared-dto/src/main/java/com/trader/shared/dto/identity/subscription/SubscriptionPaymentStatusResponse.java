package com.trader.shared.dto.identity.subscription;

import com.trader.shared.enums.PaymentStatus;
import com.trader.shared.enums.SubscriptionPlan;

public class SubscriptionPaymentStatusResponse {

    private Long id;
    private PaymentStatus status;
    private SubscriptionPlan plan;
    private boolean autoRenewal;

    public SubscriptionPaymentStatusResponse() {
    }

    public SubscriptionPaymentStatusResponse(Long id, PaymentStatus status, SubscriptionPlan plan,
            boolean autoRenewal) {
        this.id = id;
        this.status = status;
        this.plan = plan;
        this.autoRenewal = autoRenewal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

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