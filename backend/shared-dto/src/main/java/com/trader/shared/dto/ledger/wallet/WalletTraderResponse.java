package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletTraderResponse {
    private Long id;
    private String address;
    private NetworkType network;

    public WalletTraderResponse() {
    }

    public WalletTraderResponse(Long id, String address, NetworkType network) {
        this.id = id;
        this.address = address;
        this.network = network;
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
}