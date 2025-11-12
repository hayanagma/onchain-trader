package com.trader.api.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.shared.dto.trading.TradeExecutionResponse;

import reactor.core.publisher.Flux;

@Service
public class TradingClient {

    private final WebClient webClient;

    public TradingClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://trading:8085/api/internal/trading")
                .build();
    }

    public Flux<CreateTradeRequest> getTradesByTraderId(Long traderId) {
        return WebClientUtil.handleFlux(
                webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/trades")
                                .queryParam("traderId", traderId)
                                .build())
                        .retrieve(),
                CreateTradeRequest.class);
    }

        public Flux<TradeExecutionResponse> getExecutionsByTraderId(Long traderId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/executions")
                        .queryParam("traderId", traderId)
                        .build())
                .retrieve()
                .bodyToFlux(TradeExecutionResponse.class);
    }
}