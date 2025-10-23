package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;

import reactor.core.publisher.Flux;

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
}
 