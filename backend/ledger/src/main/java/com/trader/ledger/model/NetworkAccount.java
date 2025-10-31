package com.trader.ledger.model;

import com.trader.shared.enums.NetworkType;

import jakarta.persistence.*;

@Entity
@Table(name = "network_accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "trader_id", "network" })
})
public class NetworkAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trader_id", nullable = false)
    private Long traderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NetworkType network;

    public NetworkAccount() {
    }

    public NetworkAccount(Long traderId, NetworkType network) {
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