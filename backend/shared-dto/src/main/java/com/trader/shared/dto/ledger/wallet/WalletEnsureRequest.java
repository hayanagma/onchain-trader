package com.trader.shared.dto.ledger.wallet;

public class WalletEnsureRequest {
    private Long traderId;
    private String address;
    private String network;

    public WalletEnsureRequest() {
    }

    public WalletEnsureRequest(Long traderId, String address, String network) {
        this.traderId = traderId;
        this.address = address;
        this.network = network;
    }

    public Long getTraderId() {
        return traderId;
    }

    public String getAddress() {
        return address;
    }

    public String getNetwork() {
        return network;
    }
}