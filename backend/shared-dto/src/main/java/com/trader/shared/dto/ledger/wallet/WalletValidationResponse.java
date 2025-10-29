package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletValidationResponse {

    private String address;
    private NetworkType network;

    public WalletValidationResponse() {
    }

    public WalletValidationResponse(String address, NetworkType network) {
        this.address = address;
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public NetworkType getNetwork() {
        return network;
    }
}