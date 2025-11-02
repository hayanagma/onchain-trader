package com.trader.shared.dto.identity.subscription;

import com.trader.shared.enums.SubscriptionPlan;

public class SubscriptionCreateRequest {

    private Long traderId;
    private SubscriptionPlan plan;

    public SubscriptionCreateRequest() {
    }

    public SubscriptionCreateRequest(Long traderId, SubscriptionPlan plan) {
        this.traderId = traderId;
        this.plan = plan;;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
}