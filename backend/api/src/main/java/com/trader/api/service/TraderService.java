package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.AuthClient;
import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;

import reactor.core.publisher.Mono;

@Service
public class TraderService {

    private final IdentityClient identityClient;
    private final WalletClient walletClient;
    private final TraderContext traderContext;
    private final AuthClient authClient;
    private final CurrencyClient currencyClient;

    public TraderService(IdentityClient identityClient,
            WalletClient walletClient,
            TraderContext traderContext,
            AuthClient authClient,
            CurrencyClient currencyClient) {
        this.identityClient = identityClient;
        this.walletClient = walletClient;
        this.traderContext = traderContext;
        this.authClient = authClient;
        this.currencyClient = currencyClient;
    }

    public Mono<TraderProfileResponse> getTraderProfile() {
        Long traderId = traderContext.getCurrentTraderId();

        return walletClient.getWalletsForTrader(traderId)
                .collectList()
                .flatMap(wallets -> currencyClient.getVisibleCurrencies(traderId)
                        .collectList()
                        .flatMap(currencies -> identityClient.getTraderProfile(traderId)
                                .map(trader -> new TraderProfileResponse(
                                        trader.getUsername(),
                                        wallets,
                                        currencies,
                                        trader.getUsernameChangeStatus(),
                                        trader.isSubscribed()))));
    }

    /*
     * public Flux<AdminTraderResponse> getTraders(String walletAddress) {
     * if (walletAddress != null && !walletAddress.isBlank()) {
     * return walletClient.getTraderIdByWalletAddress(walletAddress)
     * .flatMapMany(traderId -> {
     * if (traderId == null) {
     * return Flux.empty();
     * }
     * return identityClient.getTrader(traderId)
     * .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
     * .map(wallet -> new AdminTraderResponse(
     * trader.getId(),
     * trader.getUsername(),
     * trader.isBanned(),
     * trader.getBannedReason(),
     * trader.isActive(),
     * wallet,
     * trader.isSubscribed())))
     * .flux();
     * });
     * } else {
     * return identityClient.getTraders()
     * .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
     * .map(wallet -> new AdminTraderResponse(
     * trader.getId(),
     * trader.getUsername(),
     * trader.isBanned(),
     * trader.getBannedReason(),
     * trader.isActive(),
     * wallet,
     * trader.isSubscribed())));
     * }
     * }
     */
    public Mono<UsernameResponse> randomizeUsername() {
        Long traderId = traderContext.getCurrentTraderId();
        return identityClient.randomizeUsername(traderId);
    }

    public Mono<UsernameResponse> getUsername() {
        Long traderId = traderContext.getCurrentTraderId();
        return identityClient.getUsername(traderId);
    }

    public Mono<Void> updateUsername(UpdateUsernameRequest request) {
        Long traderId = traderContext.getCurrentTraderId();
        return identityClient.updateUsername(traderId, request);
    }

    public Mono<Void> deleteAccount(DeleteAccountRequest request) {
        Long traderId = traderContext.getCurrentTraderId();

        return authClient.clearTraderCookies()
                .then(walletClient.cleanupTraderWallet(traderId))
                .then(identityClient.deleteTraderAccount(traderId, request));
    }
}