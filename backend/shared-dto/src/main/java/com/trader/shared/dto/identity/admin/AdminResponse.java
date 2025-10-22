package com.trader.shared.dto.identity.admin;

public class AdminResponse {
    private String username;
    private String password;
    private Integer tokenVersion;

    public AdminResponse() {
    }

    public AdminResponse(String username, String password, Integer tokenVersion) {
        this.username = username;
        this.password = password;
        this.tokenVersion = tokenVersion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(Integer tokenVersion) {
        this.tokenVersion = tokenVersion;
    }
}
