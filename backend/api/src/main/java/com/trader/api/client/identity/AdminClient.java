package com.trader.api.client.identity;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.identity.admin.AdminTraderInternalResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionCreateRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdminClient {

    private final WebClient webClient;

    public AdminClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://identity:8083/api/internal/identity/admin")
                .build();
    }

    public Mono<AdminTraderInternalResponse> getTrader(Long traderId) {
        return WebClientUtil.handle(
                webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/traders/{id}")
                                .build(traderId))
                        .retrieve(),
                AdminTraderInternalResponse.class);
    }

    public Flux<AdminTraderInternalResponse> getTraders() {
        return WebClientUtil.handleFlux(
                webClient.get()
                        .uri("/traders")
                        .retrieve(),
                AdminTraderInternalResponse.class);
    }

    public Mono<Void> updateBanStatus(BanRequest request) {
        return WebClientUtil.handleVoid(
                webClient.put()
                        .uri("/traders/ban-status")
                        .bodyValue(request)
                        .retrieve());
    }

    public Mono<Void> createSubscription(SubscriptionCreateRequest request) {
        return WebClientUtil.handleVoid(
                webClient.post()
                        .uri("/subscriptions")
                        .bodyValue(request)
                        .retrieve());
    }

    public Mono<Void> deleteSubscription(Long traderId) {
        return WebClientUtil.handleVoid(
                webClient.post()
                        .uri(uriBuilder -> uriBuilder
                                .path("/subscriptions/{traderId}")
                                .build(traderId))
                        .retrieve());
    }

}
