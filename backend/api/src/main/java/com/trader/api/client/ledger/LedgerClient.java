package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentStatusResponse;
import com.trader.shared.dto.ledger.paymentcurrency.PaymentCurrencyResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LedgerClient {

        private final WebClient webClient;

        public LedgerClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://ledger:8082/api/internal/ledger")
                                .build();
        }

        public Mono<SubscriptionPaymentResponse> createSubscriptionPayment(Long traderId,
                        SubscriptionPaymentRequest request) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/subscription-payments")
                                                                .queryParam("traderId", traderId)
                                                                .build())
                                                .bodyValue(request)
                                                .retrieve(),
                                SubscriptionPaymentResponse.class);
        }

        public Mono<SubscriptionPaymentStatusResponse> getSubscriptionPaymentStatus(Long paymentId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/subscription-payments/{id}")
                                                                .build(paymentId))
                                                .retrieve(),
                                SubscriptionPaymentStatusResponse.class);
        }

        public Flux<PaymentCurrencyResponse> getAllPaymentCurrencies() {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .uri("/payment-currencies")
                                                .retrieve(),
                                PaymentCurrencyResponse.class);
        }

}