package com.trader.auth.dto.player;

import com.trader.shared.enums.NetworkType;

public class ChallengeRequest {

    private String walletAddress;
    private NetworkType network;

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }
}