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

        public Mono<Void> addCurrency(Long playerId, CurrencyAddRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.post()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/create")
                                                                .queryParam("playerId", playerId)
                                                                .build())
                                                .bodyValue(request)
                                                .retrieve());
        }

        public Flux<CurrencyResponse> getCurrenciesByNetwork(Long playerId, NetworkType network) {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/network/{network}")
                                                                .queryParam("playerId", playerId)
                                                                .build(network))
                                                .retrieve(),
                                CurrencyResponse.class);
        }
}
