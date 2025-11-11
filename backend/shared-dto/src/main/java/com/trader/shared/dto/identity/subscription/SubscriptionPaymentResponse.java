package com.trader.shared.dto.identity.subscription;

import java.math.BigDecimal;
import java.time.Instant;

import com.trader.shared.enums.PaymentNetworkType;
import com.trader.shared.enums.PaymentStatus;

public class SubscriptionPaymentResponse {

    private Long id;
    private String paymentCurrencyCode;
    private String depositAddress;
    private PaymentStatus status;
    private BigDecimal amount;
    private String qrCodeUrl;
    private Instant createdAt;
    private Instant expiresAt;
    private PaymentNetworkType network;

    public SubscriptionPaymentResponse() {
    }

    public SubscriptionPaymentResponse(Long id,
            String paymentCurrencyCode,
            String depositAddress,
            PaymentStatus status,
            BigDecimal amount,
            String qrCodeUrl,
            Instant createdAt,
            Instant expiresAt,
            PaymentNetworkType network) {
        this.id = id;
        this.paymentCurrencyCode = paymentCurrencyCode;
        this.depositAddress = depositAddress;
        this.status = status;
        this.amount = amount;
        this.qrCodeUrl = qrCodeUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.network = network;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentCurrencyCode() {
        return paymentCurrencyCode;
    }

    public void setPaymentCurrencyCode(String paymentCurrencyCode) {
        this.paymentCurrencyCode = paymentCurrencyCode;
    }

    public String getDepositAddress() {
        return depositAddress;
    }

    public void setDepositAddress(String depositAddress) {
        this.depositAddress = depositAddress;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public PaymentNetworkType getNetwork() {
        return network;
    }

    public void setNetwork(PaymentNetworkType network) {
        this.network = network;
    }
}
