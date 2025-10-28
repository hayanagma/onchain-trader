package com.trader.ledger.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = { "address", "network" }))
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String network;

    @Column(nullable = false, length = 128)
    private String address;

    @Column(name = "trader_id", nullable = false)
    private Long traderId;

    public Long getId() {
        return id;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}