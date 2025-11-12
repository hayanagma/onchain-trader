package com.trader.shared.dto.trading;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTradeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long traderId;

    @NotBlank
    private String walletAddress;

    @NotBlank
    private String tokenIn;

    @NotBlank
    private String tokenOut;

    @NotNull
    private BigDecimal amountIn;

    @NotNull
    private BigDecimal minAmountOut;

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
}