package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.NetworkType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyClient {

        private final WebClient webClient;

        public CurrencyClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://ledger:8082/api/internal/ledger/currencies")
                                .build();
        }

        public Flux<CurrencyResponse> getAllCurrencies() {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .retrieve(),
                                CurrencyResponse.class);
        }

        public Mono<Void> addCurrency(Long traderId, CurrencyAddRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.post()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/create")
                                                                .queryParam("traderId", traderId)
                                                                .build())
                                                .bodyValue(request)
                                                .retrieve());
        }

        public Flux<CurrencyResponse> getVisibleCurrencies(Long traderId) {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/trader")
                                                                .queryParam("traderId", traderId)
                                                                .build())
                                                .retrieve(),
                                CurrencyResponse.class);
        }
}