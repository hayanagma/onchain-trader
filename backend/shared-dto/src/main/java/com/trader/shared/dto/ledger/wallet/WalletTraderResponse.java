package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletTraderResponse {

    private String address;
    private NetworkType network;

    public WalletTraderResponse() {
    }

    public WalletTraderResponse(String address, NetworkType network) {
        this.address = address;
        this.network = network;
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