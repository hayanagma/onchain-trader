package com.trader.trading.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trade_executions")
public class TradeExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_request_id", nullable = false)
    private TradeRequest tradeRequest;

    @Column(nullable = false)
    private Long executedAt;

    @Column(length = 128)
    private String txHash;

    @Column(precision = 38, scale = 18)
    private BigDecimal executedAmount;

    @Column(precision = 38, scale = 18)
    private BigDecimal receivedAmount;

    @Column(precision = 38, scale = 18)
    private BigDecimal profitLoss;

    public Long getId() {
        return id;
    }

    public TradeRequest getTradeRequest() {
        return tradeRequest;
    }

    public void setTradeRequest(TradeRequest tradeRequest) {
        this.tradeRequest = tradeRequest;
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

    public BigDecimal getExecutedAmount() {
        return executedAmount;
    }

    public void setExecutedAmount(BigDecimal executedAmount) {
        this.executedAmount = executedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public BigDecimal getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(BigDecimal profitLoss) {
        this.profitLoss = profitLoss;
    }
}
