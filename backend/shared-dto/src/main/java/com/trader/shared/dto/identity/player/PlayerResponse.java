package com.trader.shared.dto.identity.player;

public class PlayerResponse {
    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private int tokenVersion;

    public PlayerResponse() {
    }

    public PlayerResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            int tokenVersion) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.tokenVersion = tokenVersion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isBanned() { return banned; }
    public void setBanned(boolean banned) { this.banned = banned; }

    public String getBannedReason() { return bannedReason; }
    public void setBannedReason(String bannedReason) { this.bannedReason = bannedReason; }

    public int getTokenVersion() { return tokenVersion; }
    public void setTokenVersion(int tokenVersion) { this.tokenVersion = tokenVersion; }
}
