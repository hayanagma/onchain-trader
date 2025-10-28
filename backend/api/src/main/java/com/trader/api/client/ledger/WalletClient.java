package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

import reactor.core.publisher.Mono;

@Service
public class WalletClient {

        private final WebClient webClient;

        public WalletClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://ledger:8082/api/internal/ledger/wallets")
                                .build();
        }

        public Mono<Long> getTraderIdByWalletAddress(String walletAddress) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/resolve-trader")
                                                                .queryParam("address", walletAddress)
                                                                .build())
                                                .retrieve(),
                                Long.class);
        }

        public Mono<WalletTraderResponse> getWalletForTrader(Long traderId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/wallet-by-trader")
                                                                .queryParam("traderId", traderId)
                                                                .build())
                                                .retrieve(),
                                WalletTraderResponse.class);
        }

        public Mono<Void> cleanupTraderWallet(Long traderId) {
                return webClient.post()
                                .uri("/{traderId}/cleanup", traderId)
                                .retrieve()
                                .bodyToMono(Void.class);
        }
}