package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.AuthClient;
import com.trader.api.client.identity.AdminClient;
import com.trader.api.client.identity.TraderClient;
import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.client.ledger.NetworkAccountClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.admin.AdminTraderResponse;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TraderService {

    private final WalletClient walletClient;
    private final TraderContext traderContext;
    private final AuthClient authClient;
    private final CurrencyClient currencyClient;
    private final NetworkAccountClient networkAccountClient;
    private final TraderClient traderClient;
    private final AdminClient adminClient;

    public TraderService(
            WalletClient walletClient,
            TraderContext traderContext,
            AuthClient authClient,
            CurrencyClient currencyClient, NetworkAccountClient networkAccountClient, AdminClient adminClient,
            TraderClient traderClient) {
        this.walletClient = walletClient;
        this.traderContext = traderContext;
        this.authClient = authClient;
        this.currencyClient = currencyClient;
        this.networkAccountClient = networkAccountClient;
        this.traderClient = traderClient;
        this.adminClient = adminClient;
    }

    public Mono<TraderProfileResponse> getTraderProfile() {
        Long traderId = traderContext.getCurrentTraderId();

        return walletClient.getWalletsForTrader(traderId)
                .collectList()
                .flatMap(wallets -> currencyClient.getVisibleCurrencies(traderId)
                        .collectList()
                        .flatMap(currencies -> networkAccountClient.getNetworkAccountsByTrader(traderId)
                                .collectList()
                                .flatMap(networkAccounts -> traderClient.getTraderProfile(traderId)
                                        .map(trader -> new TraderProfileResponse(
                                                trader.getId(),
                                                trader.getUsername(),
                                                wallets,
                                                currencies,
                                                networkAccounts,
                                                trader.getUsernameChangeStatus(),
                                                trader.getSubscriptionPlan(),
                                                trader.getSubscription())))));
    }

    public Flux<AdminTraderResponse> getTraders(String walletAddress) {
        if (walletAddress != null && !walletAddress.isBlank()) {
            return walletClient.getTraderIdByWalletAddress(walletAddress)
                    .flatMapMany(traderId -> {
                        if (traderId == null) {
                            return Flux.empty();
                        }
                        return adminClient.getTrader(traderId)
                                .flatMap(trader -> walletClient.getWalletsForTrader(trader.getId()).collectList()
                                        .flatMap(wallets -> currencyClient.getVisibleCurrencies(trader.getId())
                                                .collectList()
                                                .flatMap(currencies -> networkAccountClient
                                                        .getNetworkAccountsByTrader(trader.getId()).collectList()
                                                        .map(networkAccounts -> new AdminTraderResponse(
                                                                trader.getId(),
                                                                trader.getUsername(),
                                                                trader.isBanned(),
                                                                trader.getBannedReason(),
                                                                trader.isActive(),
                                                                wallets,
                                                                currencies,
                                                                networkAccounts,
                                                                trader.getSubscriptionPlan(),
                                                                trader.getSubscription())))))
                                .flux();
                    });
        } else {
            return adminClient.getTraders()
                    .flatMap(trader -> walletClient.getWalletsForTrader(trader.getId()).collectList()
                            .flatMap(wallets -> currencyClient.getVisibleCurrencies(trader.getId()).collectList()
                                    .flatMap(currencies -> networkAccountClient
                                            .getNetworkAccountsByTrader(trader.getId()).collectList()
                                            .map(networkAccounts -> new AdminTraderResponse(
                                                    trader.getId(),
                                                    trader.getUsername(),
                                                    trader.isBanned(),
                                                    trader.getBannedReason(),
                                                    trader.isActive(),
                                                    wallets,
                                                    currencies,
                                                    networkAccounts,
                                                    trader.getSubscriptionPlan(),
                                                    trader.getSubscription())))));
        }
    }

    public Mono<UsernameResponse> randomizeUsername() {
        Long traderId = traderContext.getCurrentTraderId();
        return traderClient.randomizeUsername(traderId);
    }

    public Mono<UsernameResponse> getUsername() {
        Long traderId = traderContext.getCurrentTraderId();
        return traderClient.getUsername(traderId);
    }

    public Mono<Void> updateUsername(UpdateUsernameRequest request) {
        Long traderId = traderContext.getCurrentTraderId();
        return traderClient.updateUsername(traderId, request);
    }

    public Mono<Void> deleteAccount(DeleteAccountRequest request) {
        Long traderId = traderContext.getCurrentTraderId();

        return authClient.clearTraderCookies()
                .then(walletClient.cleanupTraderWallet(traderId))
                .then(traderClient.deleteTraderAccount(traderId, request));
    }
}