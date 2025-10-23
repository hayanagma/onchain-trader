package com.trader.shared.dto.identity.player;

import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;

public class PlayerProfileResponse {
    private String username;
    private WalletPlayerResponse wallet;
    private UsernameChangeStatus usernameChangeStatus;

    public PlayerProfileResponse() {
    }

    public PlayerProfileResponse(String username,
            WalletPlayerResponse wallet, UsernameChangeStatus usernameChangeStatus) {
        this.username = username;
        this.wallet = wallet;
        this.usernameChangeStatus = usernameChangeStatus;
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

    public void setUsernameChangeStatus(UsernameChangeStatus usernameChangeStatus) {
        this.usernameChangeStatus = usernameChangeStatus;
    }

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }
    
}
