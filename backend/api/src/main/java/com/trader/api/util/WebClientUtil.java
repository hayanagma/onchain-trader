package com.trader.api.util;

import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class WebClientUtil {

    private WebClientUtil() {
    }

    private static Mono<? extends Throwable> mapErrorResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(Map.class)
                .flatMap(errorMap -> {
                    String message = null;

                    if (errorMap.get("message") != null) {
                        message = errorMap.get("message").toString();
                    } else if (errorMap.get("reason") != null) {
                        message = errorMap.get("reason").toString();
                    } else if (errorMap.get("error") != null) {
                        message = errorMap.get("error").toString();
                    } else if (errorMap.get("path") != null) {
                        message = "Error at " + errorMap.get("path");
                    }

                    if (message == null) {
                        message = clientResponse.statusCode().toString();
                    }

                    return Mono.<Throwable>error(new ResponseStatusException(clientResponse.statusCode(), message));
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(clientResponse.statusCode(),
                                clientResponse.statusCode().toString())));
    }

    public static <T> Mono<T> handle(WebClient.ResponseSpec spec, Class<T> type) {
        return spec
                .onStatus(HttpStatusCode::isError, WebClientUtil::mapErrorResponse)
                .bodyToMono(type);
    }

    public static <T> Flux<T> handleFlux(WebClient.ResponseSpec spec, Class<T> type) {
        return spec
                .onStatus(HttpStatusCode::isError, WebClientUtil::mapErrorResponse)
                .bodyToFlux(type);
    }

    public static Mono<Void> handleVoid(WebClient.ResponseSpec spec) {
        return spec
                .onStatus(HttpStatusCode::isError, WebClientUtil::mapErrorResponse)
                .bodyToMono(Void.class);
    }
}