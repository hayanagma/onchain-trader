package com.trader.shared.dto.ledger.currency;

import com.trader.shared.enums.CurrencyKind;

public class CurrencyResponse {

    private String code;
    private String network;
    private int decimals;
    private boolean enabled;
    private CurrencyKind kind;
    private String contractAddress;

    public CurrencyResponse() {
    }

    public CurrencyResponse(String code,
            String network,
            int decimals,
            boolean enabled,
            CurrencyKind kind,
            String contractAddress) {
        this.code = code;
        this.network = network;
        this.decimals = decimals;
        this.enabled = enabled;
        this.kind = kind;
        this.contractAddress = contractAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
}
