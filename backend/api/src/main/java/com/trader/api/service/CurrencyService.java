package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.security.PlayerContext;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;

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
}
