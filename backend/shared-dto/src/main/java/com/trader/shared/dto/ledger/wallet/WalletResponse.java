package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

public class WalletResponse {
    private Long id;
    private Long traderId;
    private String address;
    private NetworkType network;
    private boolean active;

    public WalletResponse() {
    }

    public WalletResponse(Long id, String address, NetworkType network, Long traderId, boolean active) {
        this.id = id;
        this.address = address;
        this.network = network;
        this.traderId = traderId;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}