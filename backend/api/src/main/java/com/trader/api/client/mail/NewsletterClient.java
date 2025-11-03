package com.trader.api.client.mail;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.mail.newsletter.NewsletterSendRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscribeRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscriberResponse;


import reactor.core.publisher.Mono;

@Service
public class NewsletterClient {

        private final WebClient webClient;

        public NewsletterClient(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder.baseUrl("http://mail:8086/api/internal/newsletter").build();
        }

        public Mono<Void> subscribe(NewsletterSubscribeRequest request) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri("/subscribe")
                                                .bodyValue(request)
                                                .retrieve(),
                                Void.class);
        }

        public Mono<Void> unsubscribe(String token) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/unsubscribe")
                                                                .queryParam("token", token)
                                                                .build())
                                                .retrieve(),
                                Void.class);
        }

        public Mono<List<NewsletterSubscriberResponse>> getAllSubscribers() {
                return webClient.get()
                                .uri("/subscribers")
                                .retrieve()
                                .bodyToFlux(NewsletterSubscriberResponse.class)
                                .collectList();
        }

        public Mono<Void> sendNewsletter(NewsletterSendRequest request) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri("/send")
                                                .bodyValue(request)
                                                .retrieve(),
                                Void.class);
        }
}