package com.trader.shared.dto.identity.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BanRequest {
    @NotNull
    private Long traderId;
    private boolean banned;
    @NotBlank
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
