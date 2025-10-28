package com.trader.shared.dto.identity.admin;

public class BanRequest {
    private Long traderId;
    private boolean banned;
    private String reason;

    public Long getTraderId() {
        return traderId;
    }

    public void getTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
