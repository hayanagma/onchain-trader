package com.casino.ledger.dto;

public class WalletValidationRequest {
    private String address;
    private String network;

    public WalletValidationRequest() {
    }

    public WalletValidationRequest(String address, String network) {
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
