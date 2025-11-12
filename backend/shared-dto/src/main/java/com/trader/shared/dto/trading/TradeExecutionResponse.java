package com.trader.shared.dto.trading;

import java.math.BigDecimal;

public class TradeExecutionResponse {

    private Long id;
    private Long tradeRequestId;
    private Long executedAt;
    private String txHash;
    private BigDecimal executedAmount;
    private BigDecimal receivedAmount;
    private BigDecimal profitLoss;

    public TradeExecutionResponse() {
    }

    public TradeExecutionResponse(Long id, Long tradeRequestId, Long executedAt, String txHash,
            BigDecimal executedAmount, BigDecimal receivedAmount, BigDecimal profitLoss) {
        this.id = id;
        this.tradeRequestId = tradeRequestId;
        this.executedAt = executedAt;
        this.txHash = txHash;
        this.executedAmount = executedAmount;
        this.receivedAmount = receivedAmount;
        this.profitLoss = profitLoss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTradeRequestId() {
        return tradeRequestId;
    }

    public void setTradeRequestId(Long tradeRequestId) {
        this.tradeRequestId = tradeRequestId;
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
