package com.trader.api.client.mail;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.mail.MailRequest;

import reactor.core.publisher.Mono;

@Service
public class MailClient {

    private final WebClient webClient;

    public MailClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://mail:8086/api/internal/mail").build();
    }

    public Mono<Void> sendContactMail(MailRequest request) {
        return WebClientUtil.handle(
                webClient.post()
                        .uri("/contact")
                        .bodyValue(request)
                        .retrieve(),
                Void.class);
    }

    public Mono<Void> sendSupportMail(MailRequest request) {
        return WebClientUtil.handle(
                webClient.post()
                        .uri("/support")
                        .bodyValue(request)
                        .retrieve(),
                Void.class);
    }

}