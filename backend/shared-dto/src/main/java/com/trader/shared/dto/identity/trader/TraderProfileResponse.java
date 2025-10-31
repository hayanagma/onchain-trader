package com.trader.shared.dto.identity.trader;

import java.util.List;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

public class TraderProfileResponse {

    private String username;
    private List<WalletTraderResponse> wallets;
    private List<CurrencyResponse> currencies;
    private UsernameChangeStatus usernameChangeStatus;
    private boolean subscribed;
    private SubscriptionResponse subscription;

    public TraderProfileResponse() {
    }

    public TraderProfileResponse(String username,
            List<WalletTraderResponse> wallets,
            List<CurrencyResponse> currencies,
            UsernameChangeStatus usernameChangeStatus,
            boolean subscribed,
            SubscriptionResponse subscription) {
        this.username = username;
        this.wallets = wallets;
        this.currencies = currencies;
        this.usernameChangeStatus = usernameChangeStatus;
        this.subscribed = subscribed;
        this.subscription = subscription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }

    public void setUsernameChangeStatus(UsernameChangeStatus usernameChangeStatus) {
        this.usernameChangeStatus = usernameChangeStatus;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public SubscriptionResponse getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionResponse subscription) {
        this.subscription = subscription;
    }
}