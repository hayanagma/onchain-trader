package com.casino.auth.dto.wallet;

public class WalletValidationResponse {
    private String address;
    private String network;

    public WalletValidationResponse() {
    }

    public WalletValidationResponse(String address, String network) {
        this.address = address;
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public String getNetwork() {
        return network;
    }
}
