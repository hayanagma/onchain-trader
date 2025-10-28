package com.trader.shared.dto.identity.admin;

import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

public class AdminTraderResponse {

    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private boolean active;
    private WalletTraderResponse wallet;
    private boolean subscribed;

    public AdminTraderResponse() {
    }

    public AdminTraderResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            boolean active,
            WalletTraderResponse wallet,
            boolean subscribed) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.active = active;
        this.wallet = wallet;
        this.subscribed = subscribed;
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

    public WalletTraderResponse getWallet() {
        return wallet;
    }

    public void setWallet(WalletTraderResponse wallet) {
        this.wallet = wallet;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}