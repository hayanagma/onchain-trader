package com.casino.ledger.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bankrolls")
public class Bankroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "currency_code", referencedColumnName = "code", unique = true)
    private Currency currency;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amount = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
