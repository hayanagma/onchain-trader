package com.trader.shared.dto.identity.trader;

import jakarta.validation.constraints.NotBlank;

public class UpdateUsernameRequest {
    @NotBlank
    private String username;

    public UpdateUsernameRequest() {
    }

    public UpdateUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
