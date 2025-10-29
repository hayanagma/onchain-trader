package com.trader.ledger.model;

import java.time.Instant;

import com.trader.shared.enums.NetworkType;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_nonces")
public class WalletNonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nonce;

    @Column(nullable = false, length = 128)
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private NetworkType network;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public WalletNonce() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}