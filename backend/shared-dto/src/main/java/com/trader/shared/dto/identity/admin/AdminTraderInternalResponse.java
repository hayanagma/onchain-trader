package com.trader.shared.dto.identity.admin;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

public class AdminTraderInternalResponse {

    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private boolean active;
    private boolean subscribed;
    private SubscriptionResponse subscription;

    public AdminTraderInternalResponse() {
    }

    public AdminTraderInternalResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            boolean active,
            boolean subscribed,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.active = active;
        this.subscribed = subscribed;
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

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBannedReason(String bannedReason) {
        this.bannedReason = bannedReason;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionResponse subscription) {
        this.subscription = subscription;
    }
}