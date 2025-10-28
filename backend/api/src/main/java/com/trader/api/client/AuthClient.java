package com.trader.api.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;

import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://auth:8081/api/internal/auth")
                .build();
    }

    public Mono<Void> clearTraderCookies() {
        return WebClientUtil.handleVoid(
                webClient.post()
                        .uri("/clear-cookies")
                        .retrieve());
    }
}