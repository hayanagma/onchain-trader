package com.trader.shared.dto.identity.admin;

import java.util.List;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;
import com.trader.shared.enums.SubscriptionPlan;

public class AdminTraderResponse {

    private Long id;
    private String username;
    private boolean banned;
    private String bannedReason;
    private boolean active;
    private List<WalletTraderResponse> wallets;
    private List<CurrencyResponse> currencies;
    private SubscriptionPlan subscriptionPlan;
    private SubscriptionResponse subscription;

    public AdminTraderResponse() {
    }

    public AdminTraderResponse(Long id,
            String username,
            boolean banned,
            String bannedReason,
            boolean active,
            List<WalletTraderResponse> wallets,
            List<CurrencyResponse> currencies,
            SubscriptionPlan subscriptionPlan,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.bannedReason = bannedReason;
        this.active = active;
        this.wallets = wallets;
        this.currencies = currencies;
        this.subscriptionPlan = subscriptionPlan;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBannedReason(String bannedReason) {
        this.bannedReason = bannedReason;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<WalletTraderResponse> getWallets() {
        return wallets;
    }

    public void setWallets(List<WalletTraderResponse> wallets) {
        this.wallets = wallets;
    }

    public List<CurrencyResponse> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyResponse> currencies) {
        this.currencies = currencies;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionResponse subscription) {
        this.subscription = subscription;
    }
}