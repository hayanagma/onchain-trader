package com.casino.ledger.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "player_balances", uniqueConstraints = @UniqueConstraint(columnNames = { "wallet_id", "currencyCode" }))
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    private Wallet wallet;

    @Column(length = 32, nullable = false)
    private String currencyCode;

    @Column(nullable = false)
    private BigDecimal reservedAmount = BigDecimal.ZERO;

    @Transient
    public BigDecimal getAvailableAmount() {
        return amount.subtract(reservedAmount);
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(BigDecimal reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet w) {
        this.wallet = w;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String code) {
        this.currencyCode = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal a) {
        this.amount = a;
    }
}