package com.trader.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.trader.TraderProfileSingleResponse;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

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

        public Mono<TraderProfileSingleResponse> createTraderWallet() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileSingleResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }

        public Mono<TraderProfileSingleResponse> getTraderWallets() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileSingleResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }

        public Mono<TraderProfileSingleResponse> deleteTraderWallet() {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTraderProfile(traderId)
                                .flatMap(trader -> walletClient.getWalletForTrader(trader.getId())
                                                .map(wallet -> new TraderProfileSingleResponse(
                                                                trader.getUsername(),
                                                                wallet,
                                                                trader.getUsernameChangeStatus(),
                                                                trader.isSubscribed())));
        }

        public Mono<WalletChallengeResponse> createChallenge(WalletChallengeRequest request) {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.createChallenge(traderId, request);
        }

        public Mono<Void> verifyAndAddWallet(WalletAddRequest request) {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.verifyAndAddWallet(traderId, request);
        }

        public Mono<List<WalletTraderResponse>> getWalletsForCurrentTrader() {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.getWalletsForTrader(traderId).collectList();
        }
}