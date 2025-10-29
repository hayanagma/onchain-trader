package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WalletValidationRequest {

    @NotBlank
    private String address;

    @NotNull
    private NetworkType network;

    public WalletValidationRequest() {
    }

    public WalletValidationRequest(String address, NetworkType network) {
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