package com.trader.trading.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.trader.shared.enums.TradeStatus;

@Entity
@Table(name = "trade_requests")
public class TradeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long traderId;

    @Column(nullable = false, length = 64)
    private String walletAddress;

    @Column(nullable = false, length = 64)
    private String tokenIn;

    @Column(nullable = false, length = 64)
    private String tokenOut;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal amountIn;

    @Column(nullable = false, precision = 38, scale = 18)
    private BigDecimal minAmountOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TradeStatus status;

    @Column(nullable = false)
    private Long createdAt;

    @Column
    private Long executedAt;

    @Column(length = 128)
    private String txHash;

    public Long getId() {
        return id;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
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

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Long executedAt) {
        this.executedAt = executedAt;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
}
