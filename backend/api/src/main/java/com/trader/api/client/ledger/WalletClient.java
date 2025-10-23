package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;

import reactor.core.publisher.Mono;

@Service
public class WalletClient {

        private final WebClient webClient;

        public WalletClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://ledger:8082/api/internal/ledger/wallets")
                                .build();
        }

        public Mono<Long> getPlayerIdByWalletAddress(String walletAddress) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/resolve-player")
                                                                .queryParam("address", walletAddress)
                                                                .build())
                                                .retrieve(),
                                Long.class);
        }
 
        public Mono<WalletPlayerResponse> getWalletForPlayer(Long playerId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/wallet-by-player")
                                                                .queryParam("playerId", playerId)
                                                                .build())
                                                .retrieve(),
                                WalletPlayerResponse.class);
        }
 
        public Mono<Void> cleanupPlayerWallet(Long playerId) {
                return webClient.post()
                                .uri("/{playerId}/cleanup", playerId)
                                .retrieve()
                                .bodyToMono(Void.class);
        }
 
}
 