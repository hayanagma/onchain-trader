package com.casino.auth.dto.wallet;

public class WalletSignatureValidationRequest {
    private String address;
    private String network;
    private String nonce;
    private String signature;

    public WalletSignatureValidationRequest() {
    }

    public WalletSignatureValidationRequest(String address, String network, String nonce, String signature) {
        this.address = address;
        this.network = network;
        this.nonce = nonce;
        this.signature = signature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
