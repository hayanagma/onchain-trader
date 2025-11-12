package com.trader.trading.model;


import jakarta.persistence.*;
import java.math.BigDecimal;

import com.trader.shared.enums.StrategyType;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private StrategyType strategyType;

    @Column(nullable = false, length = 64)
    private String userAddress;

    @Column(nullable = false, length = 64)
    private String tokenIn;

    @Column(nullable = false, length = 64)
    private String tokenOut;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amountIn;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal minAmountOut;

    @Column(precision = 38, scale = 18)
    private BigDecimal priceTarget;

    @Column
    private Long intervalSeconds;

    @Column(nullable = false)
    private Long expiry;

    @Column(nullable = false)
    private Long nonce;

    @Column(nullable = false, length = 132)
    private String signature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;

    public Long getId() {
        return id;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getTokenIn() {
        return tokenIn;
    }

    public void setTokenIn(String tokenIn) {
        this.tokenIn = tokenIn;
    }

    public String getTokenOut() {
        return tokenOut;
    }

    public void setTokenOut(String tokenOut) {
        this.tokenOut = tokenOut;
    }

    public BigDecimal getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(BigDecimal amountIn) {
        this.amountIn = amountIn;
    }

    public BigDecimal getMinAmountOut() {
        return minAmountOut;
    }

    public void setMinAmountOut(BigDecimal minAmountOut) {
        this.minAmountOut = minAmountOut;
    }

    public BigDecimal getPriceTarget() {
        return priceTarget;
    }

    public void setPriceTarget(BigDecimal priceTarget) {
        this.priceTarget = priceTarget;
    }

    public Long getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(Long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
}
