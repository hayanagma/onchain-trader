package com.trader.ledger.model;

import com.trader.shared.enums.CurrencyKind;

import jakarta.persistence.*;

@Entity
@Table(name = "currencies", indexes = {
        @Index(name = "idx_currency_network_player", columnList = "network, player_id")
})
public class Currency {
    @Id
    @Column(length = 32)
    private String code;

    @Column(nullable = false, length = 16)
    private String network;

    @Column(nullable = false)
    private int decimals;

    @Column(nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CurrencyKind kind;

    @Column(length = 128)
    private String contractAddress;

    @Column(name = "player_id")
    private Long playerId;

    public String getCode() {
        return code;
    }

    public void setCode(String c) {
        this.code = c;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String n) {
        this.network = n;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int d) {
        this.decimals = d;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean e) {
        this.enabled = e;
    }

    public CurrencyKind getKind() {
        return kind;
    }

    public void setKind(CurrencyKind k) {
        this.kind = k;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String a) {
        this.contractAddress = a;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}