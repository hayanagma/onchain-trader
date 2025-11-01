package com.trader.shared.dto.identity.trader;

import java.util.List;

import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;
import com.trader.shared.enums.SubscriptionPlan;

public class TraderProfileResponse {

    private Long id;
    private String username;
    private List<WalletTraderResponse> wallets;
    private List<CurrencyResponse> currencies;
    private List<NetworkAccountResponse> networkAccounts;
    private UsernameChangeStatus usernameChangeStatus;
    private SubscriptionPlan subscriptionPlan;
    private SubscriptionResponse subscription;

    public TraderProfileResponse() {
    }

    public TraderProfileResponse(Long id,
            String username,
            List<WalletTraderResponse> wallets,
            List<CurrencyResponse> currencies,
            List<NetworkAccountResponse> networkAccounts,
            UsernameChangeStatus usernameChangeStatus,
            SubscriptionPlan subscriptionPlan,
            SubscriptionResponse subscription) {
        this.id = id;
        this.username = username;
        this.wallets = wallets;
        this.currencies = currencies;
        this.networkAccounts = networkAccounts;
        this.usernameChangeStatus = usernameChangeStatus;
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

    public List<NetworkAccountResponse> getNetworkAccounts() {
        return networkAccounts;
    }

    public void setNetworkAccounts(List<NetworkAccountResponse> networkAccounts) {
        this.networkAccounts = networkAccounts;
    }

    public UsernameChangeStatus getUsernameChangeStatus() {
        return usernameChangeStatus;
    }

    public void setUsernameChangeStatus(UsernameChangeStatus usernameChangeStatus) {
        this.usernameChangeStatus = usernameChangeStatus;
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