package com.trader.shared.dto.identity.subscription;

import java.time.Instant;

import com.trader.shared.enums.SubscriptionPlan;

public class SubscriptionResponse {

    private SubscriptionPlan plan;
    private boolean active;
    private boolean autoRenewal;
    private Instant startDate;
    private Instant endDate;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(
            SubscriptionPlan plan,
            boolean active,
            boolean autoRenewal,
            Instant startDate,
            Instant endDate) {
        this.plan = plan;
        this.active = active;
        this.autoRenewal = autoRenewal;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SubscriptionResponse inactive() {
        SubscriptionResponse response = new SubscriptionResponse();
        response.setActive(false);
        return response;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}