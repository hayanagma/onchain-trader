package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletResponse {

    private Long id;
    private Long traderId;
    private String address;
    private NetworkType network;

    public WalletResponse() {
    }

    public WalletResponse(Long id, String address, NetworkType network, Long traderId) {
        this.id = id;
        this.address = address;
        this.network = network;
        this.traderId = traderId;
    }

    public Long getId() {
        return id;
    }

    public Long getTraderId() {
        return traderId;
    }

    public String getAddress() {
        return address;
    }

    public NetworkType getNetwork() {
        return network;
    }
}