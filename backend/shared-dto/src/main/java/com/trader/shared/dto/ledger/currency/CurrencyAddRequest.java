package com.trader.shared.dto.ledger.currency;

import com.trader.shared.enums.NetworkType;

public class CurrencyAddRequest {

    private NetworkType network;
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