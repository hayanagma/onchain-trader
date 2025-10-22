package com.trader.shared.dto.identity.player;

public class UsernameResponse {
    private String username;

    public UsernameResponse() {
    }

    public UsernameResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}