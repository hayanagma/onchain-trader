package com.trader.shared.dto.ledger.currency;

import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.NetworkType;

public class CurrencyResponse {

    private String code;
    private NetworkType network;
    private int decimals;
    private CurrencyKind kind;
    private String contractAddress;

    public CurrencyResponse() {
    }

    public CurrencyResponse(String code,
            NetworkType network,
            int decimals,
            CurrencyKind kind,
            String contractAddress) {
        this.code = code;
        this.network = network;
        this.decimals = decimals;
        this.kind = kind;
        this.contractAddress = contractAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
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
}
