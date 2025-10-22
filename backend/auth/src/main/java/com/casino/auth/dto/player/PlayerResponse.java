package com.casino.auth.dto.player;

public class PlayerResponse {
    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private Integer tokenVersion;

    public PlayerResponse(Long id, String username, boolean banned, String bannedReason, Integer tokenVersion) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.tokenVersion = tokenVersion;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public Integer getTokenVersion() {
        return tokenVersion;
    }
}
