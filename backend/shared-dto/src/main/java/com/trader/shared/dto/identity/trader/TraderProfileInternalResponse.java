package com.trader.shared.dto.identity.trader;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.enums.SubscriptionPlan;

public class TraderProfileInternalResponse {

    private Long id;
    private String username;
    private UsernameChangeStatus usernameChangeStatus;
    private SubscriptionPlan subscriptionPlan;
    private SubscriptionResponse subscription;

    public TraderProfileInternalResponse() {
    }

    public TraderProfileInternalResponse(Long id,
            String username,
            UsernameChangeStatus usernameChangeStatus,
            SubscriptionPlan subscriptionPlan,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.usernameChangeStatus = usernameChangeStatus;
        this.subscriptionPlan = subscriptionPlan;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }

    public void setUsernameChangeStatus(UsernameChangeStatus usernameChangeStatus) {
        this.usernameChangeStatus = usernameChangeStatus;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionResponse subscription) {
        this.subscription = subscription;
    }
}