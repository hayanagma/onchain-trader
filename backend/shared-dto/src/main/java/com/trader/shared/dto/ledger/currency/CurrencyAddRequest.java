package com.trader.shared.dto.ledger.currency;

import com.trader.shared.enums.NetworkType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CurrencyAddRequest {
    
    @NotNull
    private NetworkType network;
    @NotBlank
    private String contractAddress;

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}