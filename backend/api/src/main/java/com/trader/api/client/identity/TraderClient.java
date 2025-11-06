package com.trader.api.client.identity;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.identity.subscription.SubscriptionCreateRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileInternalResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;

import reactor.core.publisher.Mono;

@Service
public class TraderClient {

    private final WebClient webClient;

    public TraderClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://identity:8083/api/internal/identity/traders")
                .build();
    }

    public Mono<TraderProfileInternalResponse> getTraderProfile(Long traderId) {
        return WebClientUtil.handle(
                webClient.get()
                        .uri("/{id}/profile", traderId)
                        .retrieve(),
                TraderProfileInternalResponse.class);
    }

    public Mono<UsernameResponse> randomizeUsername(Long traderId) {
        return WebClientUtil.handle(
                webClient.post()
                        .uri("/{id}/profile/random-username", traderId)
                        .retrieve(),
                UsernameResponse.class);
    }

    public Mono<UsernameResponse> getUsername(Long traderId) {
        return WebClientUtil.handle(
                webClient.get()
                        .uri("/{id}/profile/username", traderId)
                        .retrieve(),
                UsernameResponse.class);
    }

    public Mono<Void> updateUsername(Long traderId, UpdateUsernameRequest request) {
        return WebClientUtil.handleVoid(
                webClient.put()
                        .uri("/{id}/profile/username", traderId)
                        .bodyValue(request)
                        .retrieve());
    }

    public Mono<Void> deleteTraderAccount(Long traderId, DeleteAccountRequest request) {
        return WebClientUtil.handleVoid(
                webClient.post()
                        .uri("/{id}/delete", traderId)
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

    public Mono<SubscriptionResponse> getSubscriptionByTraderId(Long traderId) {
        return WebClientUtil.handle(
                webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/subscriptions/{traderId}")
                                .build(traderId))
                        .retrieve(),
                SubscriptionResponse.class);
    }

}
