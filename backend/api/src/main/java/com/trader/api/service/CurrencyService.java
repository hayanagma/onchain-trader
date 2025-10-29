package com.trader.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;

import reactor.core.publisher.Mono;

@Service
public class CurrencyService {

    private final TraderContext traderContext;
    private final CurrencyClient currencyClient;

    public CurrencyService(TraderContext traderContext, CurrencyClient currencyClient) {
        this.traderContext = traderContext;
        this.currencyClient = currencyClient;
    }

    public Mono<Void> addCurrencyForCurrentTrader(CurrencyAddRequest request) {
        Long traderId = traderContext.getCurrentTraderId();
        return currencyClient.addCurrency(traderId, request);
    }

    public Mono<List<CurrencyResponse>> getCurrenciesForCurrentTrader() {
        Long traderId = traderContext.getCurrentTraderId();
        return currencyClient.getVisibleCurrencies(traderId)
                .collectList();
    }

    public Mono<Void> removeCurrencyForCurrentTrader(Long currencyId) {
        Long traderId = traderContext.getCurrentTraderId();
        return currencyClient.removeCurrency(traderId, currencyId);
    }
}