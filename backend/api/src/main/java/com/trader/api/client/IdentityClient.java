package com.trader.api.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.identity.admin.AdminTraderInternalResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileInternalResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IdentityClient {

        private final WebClient webClient;

        public IdentityClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://identity:8083/api/internal/identity")
                                .build();
        }

        public Mono<TraderProfileInternalResponse> getTraderProfile(Long traderId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri("/traders/{id}/profile", traderId)
                                                .retrieve(),
                                TraderProfileInternalResponse.class);
        }

        public Mono<AdminTraderInternalResponse> getTrader(Long traderId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/admin/traders/{id}")
                                                                .build(traderId))
                                                .retrieve(),
                                AdminTraderInternalResponse.class);
        }

        public Flux<AdminTraderInternalResponse> getTraders() {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .uri("/admin/traders")
                                                .retrieve(),
                                AdminTraderInternalResponse.class);
        }

        public Mono<Void> updateBanStatus(BanRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.put()
                                                .uri("/admin/traders/ban-status")
                                                .bodyValue(request)
                                                .retrieve());
        }

        public Mono<UsernameResponse> randomizeUsername(Long traderId) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri("/traders/{id}/profile/random-username", traderId)
                                                .retrieve(),
                                UsernameResponse.class);
        }

        public Mono<UsernameResponse> getUsername(Long traderId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri("/traders/{id}/profile/username", traderId)
                                                .retrieve(),
                                UsernameResponse.class);
        }

        public Mono<Void> updateUsername(Long traderId, UpdateUsernameRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.put()
                                                .uri("/traders/{id}/profile/username", traderId)
                                                .bodyValue(request)
                                                .retrieve());
        }

        public Mono<Void> deleteTraderAccount(Long traderId, DeleteAccountRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.post()
                                                .uri("/traders/{id}/delete", traderId)
                                                .bodyValue(request)
                                                .retrieve());
        }
}