package com.trader.shared.dto.identity.player;

public class PlayerProfileInternalResponse {
    private Long id;
    private String username;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;

    public PlayerProfileInternalResponse() {
    }

    public PlayerProfileInternalResponse(Long id,
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