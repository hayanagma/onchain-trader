package com.trader.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;
import com.trader.shared.enums.SubscriptionPlan;

import reactor.core.publisher.Mono;

@Service
public class WalletService {

        private final WalletClient walletClient;
        private final TraderContext traderContext;
        private final IdentityClient identityClient;

        public WalletService(WalletClient walletClient, TraderContext traderContext, IdentityClient identityClient) {
                this.walletClient = walletClient;
                this.traderContext = traderContext;
                this.identityClient = identityClient;
        }

        public Mono<WalletChallengeResponse> createChallenge(WalletChallengeRequest request) {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.createChallenge(traderId, request);
        }

        public Mono<List<WalletTraderResponse>> getWalletsForCurrentTrader() {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.getWalletsForTrader(traderId).collectList();
        }

        public Mono<Void> removeWalletForCurrentTrader(Long walletId) {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.removeWallet(traderId, walletId);
        }

        public Mono<Void> verifyAndAddWallet(WalletAddRequest request) {
                Long traderId = traderContext.getCurrentTraderId();

                return identityClient.getTrader(traderId)
                                .flatMap(trader -> {
                                        if (trader.getSubscriptionPlan() == SubscriptionPlan.FREE) {
                                                return Mono.<Void>error(new ResponseStatusException(
                                                                HttpStatus.BAD_REQUEST,
                                                                "You need a subscription plan to add multiple wallets."));
                                        }

                                        return walletClient.verifyAndAddWallet(traderId, request);
                                });
        }
}