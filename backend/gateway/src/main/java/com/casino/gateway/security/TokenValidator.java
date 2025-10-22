package com.casino.gateway.security;

import java.time.Duration;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@Component
public class TokenValidator {

    private final WebClient webClient;
    private final ReactiveStringRedisTemplate redis;

    public TokenValidator(WebClient.Builder webClientBuilder, ReactiveStringRedisTemplate redis) {
        this.webClient = webClientBuilder
                .baseUrl("http://identity:8083/api/internal/identity")
                .build();
        this.redis = redis;
    }

    public Mono<Void> validate(String role, String subject, int tokenVersion) {
        String key = role + ":" + subject;

        return redis.opsForValue().get(key)
                .flatMap((String cached) -> {
                    int cachedVersion = Integer.parseInt(cached);
                    if (cachedVersion == tokenVersion) {
                        return Mono.<Void>empty();
                    }
                    return Mono.<Void>error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token revoked"));
                })
                .switchIfEmpty(fetchAndCache(role, subject, key, tokenVersion));
    }

    private Mono<Void> fetchAndCache(String role, String subject, String key, int tokenVersion) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/token-version")
                        .queryParam("role", role)
                        .queryParam("subject", subject)
                        .build())
                .retrieve()
                .bodyToMono(Integer.class)
                .flatMap((Integer actualVersion) -> {
                    Mono<Boolean> cacheWrite = redis.opsForValue()
                            .set(key, String.valueOf(actualVersion), Duration.ofSeconds(60));

                    if (actualVersion == tokenVersion) {
                        return cacheWrite.then(Mono.<Void>empty());
                    } else {
                        return cacheWrite.then(Mono.<Void>error(
                                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token revoked")));
                    }
                });
    }
}