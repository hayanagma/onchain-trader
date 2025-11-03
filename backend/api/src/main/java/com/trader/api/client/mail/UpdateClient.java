package com.trader.api.client.mail;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.mail.update.UpdateCreate;
import com.trader.shared.dto.mail.update.UpdateEditRequest;
import com.trader.shared.dto.mail.update.UpdateResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UpdateClient {

    private final WebClient webClient;

    public UpdateClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://mail:8086/api/internal/updates").build();
    }

    public Mono<Void> createUpdate(UpdateCreate request) {
        return WebClientUtil.handle(
                webClient.post()
                        .uri("/create")
                        .bodyValue(request)
                        .retrieve(),
                Void.class);
    }

    public Mono<Void> editUpdate(UpdateEditRequest request) {
        return webClient.put()
                .uri("/edit")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<UpdateResponse> getAllUpdates() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(UpdateResponse.class);
    }
}