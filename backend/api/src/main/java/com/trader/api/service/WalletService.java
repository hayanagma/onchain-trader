package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.trader.TraderProfileResponse;

import reactor.core.publisher.Mono;

@Service
public class WalletService {

        private final IdentityClient identityClient;
        private final WalletClient walletClient;
        private final TraderContext traderContext;

        public WalletService(IdentityClient identityClient, WalletClient walletClient, TraderContext traderContext) {
                this.identityClient = identityClient;
                this.walletClient = walletClient;
                this.traderContext = traderContext;
        }

        public Mono<TraderProfileResponse> createTraderWallet() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }

        public Mono<TraderProfileResponse> getTraderWallets() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }

        public Mono<TraderProfileResponse> deleteTraderWallet() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }
}