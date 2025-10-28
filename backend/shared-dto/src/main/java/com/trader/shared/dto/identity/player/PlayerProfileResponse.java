package com.trader.shared.dto.identity.player;

import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;

public class PlayerProfileResponse {
    private String username;
    private WalletPlayerResponse wallet;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;

    public PlayerProfileResponse() {
    }

    public PlayerProfileResponse(String username,
            WalletPlayerResponse wallet,
            UsernameChangeStatus usernameChangeStatus,
            boolean subscribed) {
        this.username = username;
        this.wallet = wallet;
        this.usernameChangeStatus = usernameChangeStatus;
        this.subscribed = subscribed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WalletPlayerResponse getWallet() {
        return wallet;
    }

    public void setWallet(WalletPlayerResponse wallet) {
        this.wallet = wallet;
    }

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }

    public void setUsernameChangeStatus(UsernameChangeStatus usernameChangeStatus) {
        this.usernameChangeStatus = usernameChangeStatus;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}