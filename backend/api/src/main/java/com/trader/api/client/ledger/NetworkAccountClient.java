package com.trader.api.client.ledger;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountResponse;

import reactor.core.publisher.Flux;

@Service
public class NetworkAccountClient {

    private final WebClient webClient;

    public NetworkAccountClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://ledger:8082/api/internal/ledger/network-accounts")
                .build();
    }

    public Flux<NetworkAccountResponse> getNetworkAccountsByTrader(Long traderId) {
        return WebClientUtil.handleFlux(
                webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/by-trader")
                                .queryParam("traderId", traderId)
                                .build())
                        .retrieve(),
                NetworkAccountResponse.class);
    }
}