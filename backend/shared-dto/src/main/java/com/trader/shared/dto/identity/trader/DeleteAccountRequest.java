package com.trader.shared.dto.identity.trader;

import jakarta.validation.constraints.NotBlank;

public class DeleteAccountRequest {
    @NotBlank
    private String confirmation;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}