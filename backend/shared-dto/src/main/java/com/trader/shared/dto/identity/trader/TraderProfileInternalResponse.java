package com.trader.shared.dto.identity.trader;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

public class TraderProfileInternalResponse {

    private Long id;
    private String username;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;
    private SubscriptionResponse subscription;

    public TraderProfileInternalResponse() {
    }

    public TraderProfileInternalResponse(Long id,
            String username,
            UsernameChangeStatus usernameChangeStatus,
            boolean subscribed,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.usernameChangeStatus = usernameChangeStatus;
        this.subscribed = subscribed;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionResponse subscription) {
        this.subscription = subscription;
    }
}