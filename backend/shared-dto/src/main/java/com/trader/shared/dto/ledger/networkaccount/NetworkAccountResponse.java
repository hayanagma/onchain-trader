package com.trader.shared.dto.ledger.networkaccount;

import com.trader.shared.enums.NetworkType;

public class NetworkAccountResponse {

    private Long id;
    private Long traderId;
    private NetworkType network;

    public NetworkAccountResponse() {
    }

    public NetworkAccountResponse(Long id, Long traderId, NetworkType network) {
        this.id = id;
        this.traderId = traderId;
        this.network = network;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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