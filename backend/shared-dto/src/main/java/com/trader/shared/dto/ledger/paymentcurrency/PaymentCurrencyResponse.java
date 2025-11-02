package com.trader.shared.dto.ledger.paymentcurrency;

import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.PaymentNetworkType;

public class PaymentCurrencyResponse {

    private Long id;
    private String code;
    private String name;
    private PaymentNetworkType network;
    private int decimals;
    private CurrencyKind kind;
    private String contractAddress;
    private boolean enabled;

    public PaymentCurrencyResponse() {
    }

    public PaymentCurrencyResponse(Long id,
            String code,
            String name,
            PaymentNetworkType network,
            int decimals,
            CurrencyKind kind,
            String contractAddress,
            boolean enabled) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.network = network;
        this.decimals = decimals;
        this.kind = kind;
        this.contractAddress = contractAddress;
        this.enabled = enabled;
    }

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