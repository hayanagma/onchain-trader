package com.trader.shared.dto.ledger.wallet;

public class WalletEnsureRequest {
    private Long playerId;
    private String address;
    private String network;

    public WalletEnsureRequest() {
    }

    public WalletEnsureRequest(Long playerId, String address, String network) {
        this.playerId = playerId;
        this.address = address;
        this.network = network;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getAddress() {
        return address;
    }

    public String getNetwork() {
        return network;
    }
}
