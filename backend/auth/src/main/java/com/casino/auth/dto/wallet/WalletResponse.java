package com.casino.auth.dto.wallet;

public class WalletResponse {

    private Long id;
    private Long playerId;
    private String address;
    private String network;

    public WalletResponse() {
    }

    public WalletResponse(Long id, String address, String network, Long playerId) {
        this.id = id;
        this.address = address;
        this.network = network;
        this.playerId = playerId;
    }

    public Long getId() {
        return id;
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
