package com.trader.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.service.CurrencyService;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addCurrency(@RequestBody CurrencyAddRequest request) {
        return currencyService.addCurrencyForCurrentTrader(request)
                .thenReturn(ResponseEntity.ok().build());
    }

    @GetMapping
    public Mono<List<CurrencyResponse>> getCurrenciesForTrader() {
        return currencyService.getCurrenciesForCurrentTrader();
    }

    @PostMapping("/remove")
    public Mono<ResponseEntity<Void>> removeCurrency(@RequestParam Long currencyId) {
        return currencyService.removeCurrencyForCurrentTrader(currencyId)
                .thenReturn(ResponseEntity.ok().build());
    }
}