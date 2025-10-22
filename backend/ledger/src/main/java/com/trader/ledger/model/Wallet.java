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

    @Column(name = "player_id", nullable = false)
    private Long playerId; 

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String n) {
        this.network = n;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String a) {
        this.address = a;
    }
}
