package com.trader.auth.security;

public enum RefreshTokenCookie {
    TRADER("TRADER_REFRESH"),
    ADMIN("ADMIN_REFRESH");

    private final String name;

    RefreshTokenCookie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}