package com.trader.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.security.PlayerContext;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.NetworkType;

import reactor.core.publisher.Mono;

@Service
public class CurrencyService {

    private final PlayerContext playerContext;
    private final CurrencyClient currencyClient;

    public CurrencyService(PlayerContext playerContext, CurrencyClient currencyClient) {
        this.playerContext = playerContext;
        this.currencyClient = currencyClient;
    }

    public Mono<Void> addCurrencyForCurrentPlayer(CurrencyAddRequest request) {
        Long playerId = playerContext.getCurrentPlayerId();
        return currencyClient.addCurrency(playerId, request);
    }

    public Mono<List<CurrencyResponse>> getCurrenciesForCurrentPlayer(NetworkType network) {
        Long playerId = playerContext.getCurrentPlayerId();
        return currencyClient.getCurrenciesByNetwork(playerId, network)
                .collectList();
    }
}
