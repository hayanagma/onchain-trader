package com.trader.shared.dto.identity.trader;

import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

public class TraderProfileResponse {
    private String username;
    private WalletTraderResponse wallet;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;

    public TraderProfileResponse() {
    }

    public TraderProfileResponse(String username,
            WalletTraderResponse wallet,
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

    public WalletTraderResponse getWallet() {
        return wallet;
    }

    public void setWallet(WalletTraderResponse wallet) {
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