package com.trader.shared.dto.ledger.networkaccount;

import com.trader.shared.enums.NetworkType;

import jakarta.validation.constraints.NotNull;

public class NetworkAccountCreateRequest {

    @NotNull
    private Long traderId;
    @NotNull
    private NetworkType network;

    public NetworkAccountCreateRequest() {
    }

    public NetworkAccountCreateRequest(Long traderId, NetworkType network) {
        this.traderId = traderId;
        this.network = network;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }
}