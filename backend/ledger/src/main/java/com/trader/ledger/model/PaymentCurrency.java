package com.trader.ledger.model;

import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.PaymentNetworkType;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_currencies", uniqueConstraints = @UniqueConstraint(columnNames = { "code", "network" }))
public class PaymentCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String code;

    @Column(nullable = false, length = 64)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PaymentNetworkType network;

    @Column(nullable = false)
    private int decimals;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CurrencyKind kind; // NATIVE or TOKEN

    @Column(length = 128)
    private String contractAddress;

    @Column(nullable = false)
    private boolean enabled = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentNetworkType getNetwork() {
        return network;
    }

    public void setNetwork(PaymentNetworkType network) {
        this.network = network;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public CurrencyKind getKind() {
        return kind;
    }

    public void setKind(CurrencyKind kind) {
        this.kind = kind;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}