package com.trader.shared.dto.ledger.wallet;

import com.trader.shared.enums.NetworkType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WalletAddRequest {

    @NotBlank
    private String address;
    @NotNull
    private NetworkType network;
    @NotBlank
    private String signature;
    @NotBlank
    private String nonce;

    public WalletAddRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}