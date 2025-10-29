package com.trader.shared.dto.ledger.wallet;



public class WalletChallengeResponse {
   
    private String nonce;

    public WalletChallengeResponse() {
    }

    public WalletChallengeResponse(String nonce) {
        this.nonce = nonce;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}