package com.trader.shared.dto.identity.trader;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

public class TraderResponse {

    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private int tokenVersion;
    private boolean subscribed;
    private SubscriptionResponse subscription;

    public TraderResponse() {
    }

    public TraderResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            int tokenVersion,
            boolean subscribed,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.tokenVersion = tokenVersion;
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

    public int getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(int tokenVersion) {
        this.tokenVersion = tokenVersion;
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