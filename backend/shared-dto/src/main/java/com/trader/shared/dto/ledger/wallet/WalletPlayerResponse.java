package com.trader.shared.dto.ledger.wallet;

public class WalletPlayerResponse {
    private String address;
    private String network;

    public WalletPlayerResponse() {
    }

    public WalletPlayerResponse(String address, String network) {
        this.address = address;
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}
