package com.trader.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.service.CurrencyService;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.NetworkType;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/player/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyClient currencyClient;

    public CurrencyController(CurrencyService currencyService, CurrencyClient currencyClient) {
        this.currencyService = currencyService;
        this.currencyClient = currencyClient;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addCurrency(@RequestBody CurrencyAddRequest request) {
        return currencyService.addCurrencyForCurrentPlayer(request)
        
                .thenReturn(ResponseEntity.ok().build());
    }

    @GetMapping
    public Mono<List<CurrencyResponse>> getCurrenciesByNetwork(@RequestParam NetworkType network) {
        return currencyClient.getCurrenciesByNetwork(network).collectList();
    }

}