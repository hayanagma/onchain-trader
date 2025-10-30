package com.trader.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

import reactor.core.publisher.Mono;

@Service
public class WalletService {

        private final WalletClient walletClient;
        private final TraderContext traderContext;

        public WalletService(WalletClient walletClient, TraderContext traderContext) {
                this.walletClient = walletClient;
                this.traderContext = traderContext;
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

        public Mono<Void> removeWalletForCurrentTrader(Long walletId) {
                Long traderId = traderContext.getCurrentTraderId();
                return walletClient.removeWallet(traderId, walletId);
        }
}