package com.trader.shared.dto.identity.admin;

import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;

public class AdminPlayerResponse {

    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private boolean active;
    private WalletPlayerResponse wallet;

    public AdminPlayerResponse() {
    }

    public AdminPlayerResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            boolean active,
            WalletPlayerResponse wallet) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.active = active;
        this.wallet = wallet;
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

    public WalletPlayerResponse getWallet() {
        return wallet;
    }

    public void setWallet(WalletPlayerResponse wallet) {
        this.wallet = wallet;
    }
}
