package com.trader.shared.dto.identity.player;

public class PlayerProfileInternalResponse {
    private Long id;
    private String username;
    private UsernameChangeStatus usernameChangeStatus;

    public PlayerProfileInternalResponse() {
    }

    public PlayerProfileInternalResponse(Long id,
            String username,
            UsernameChangeStatus usernameChangeStatus) {
        this.id = id;
        this.username = username;
        this.usernameChangeStatus = usernameChangeStatus;
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

}