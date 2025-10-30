package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletTraderResponse {
    private Long id;
    private String address;
    private NetworkType network;
    private boolean active;

    public WalletTraderResponse() {
    }

    public WalletTraderResponse(Long id, String address, NetworkType network, boolean active) {
        this.id = id;
        this.address = address;
        this.network = network;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}