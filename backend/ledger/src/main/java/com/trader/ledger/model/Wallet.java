package com.trader.ledger.model;

import com.trader.shared.enums.NetworkType;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = { "address", "network" }))
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private NetworkType network;

    @Column(nullable = false, length = 128)
    private String address;

    @Column(name = "trader_id", nullable = false)
    private Long traderId;


    @Column(nullable = false)
    private boolean active = true;

    public Wallet() {
    }

    public Wallet(NetworkType network, String address, Long traderId) {
        this.network = network;
        this.address = address;
        this.traderId = traderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}