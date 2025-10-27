package com.trader.ledger.dto;

public class TokenMetadata {
    private final String name;
    private final String symbol;
    private final int decimals;

    public TokenMetadata(String name, String symbol, int decimals) {
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDecimals() {
        return decimals;
    }
}