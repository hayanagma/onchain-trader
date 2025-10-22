package com.casino.ledger.model;

import jakarta.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @Column(length = 32)
    private String code; // "USDT-TRC20", "BTC", "SOL", etc.

    @Column(nullable = false, length = 16)
    private String network; // "TRON"

    @Column(nullable = false)
    private int decimals;

    @Column(nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private CurrencyKind kind; // TOKEN or NATIVE

    @Column(length = 128)
    private String contractAddress; 

    public String getCode() { return code; }
    public void setCode(String c) { this.code = c; }

    public String getNetwork() { return network; }
    public void setNetwork(String n) { this.network = n; }

    public int getDecimals() { return decimals; }
    public void setDecimals(int d) { this.decimals = d; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean e) { this.enabled = e; }

    public CurrencyKind getKind() { return kind; }
    public void setKind(CurrencyKind k) { this.kind = k; }

    public String getContractAddress() { return contractAddress; }
    public void setContractAddress(String a) { this.contractAddress = a; }
}