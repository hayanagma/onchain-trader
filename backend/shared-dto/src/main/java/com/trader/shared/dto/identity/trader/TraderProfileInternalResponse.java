package com.trader.shared.dto.identity.trader;

public class TraderProfileInternalResponse {
    private Long id;
    private String username;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;

    public TraderProfileInternalResponse() {
    }

    public TraderProfileInternalResponse(Long id,
            String username,
            UsernameChangeStatus usernameChangeStatus,
            boolean subscribed) {
        this.id = id;
        this.username = username;
        this.usernameChangeStatus = usernameChangeStatus;
        this.subscribed = subscribed;
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
}