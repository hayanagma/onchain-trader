package com.trader.auth.dto.player;


public class ChallengeResponse  {
    private String nonce;

    public ChallengeResponse(String nonce) {
        this.nonce = nonce;
    }

    public String getNonce() {
        return nonce;
    }
}
