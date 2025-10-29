package com.trader.ledger.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.CurrencyService;
import com.trader.ledger.service.TraderCurrencyService;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;

@RestController
@RequestMapping("/api/internal/ledger/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final TraderCurrencyService traderCurrencyService;

    public CurrencyController(CurrencyService currencyService, TraderCurrencyService traderCurrencyService) {
        this.currencyService = currencyService;
        this.traderCurrencyService = traderCurrencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCurrency(@RequestParam Long traderId,
            @RequestBody CurrencyAddRequest request) {
        currencyService.createCurrency(traderId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trader")
    public ResponseEntity<List<CurrencyResponse>> getCurrenciesForTrader(@RequestParam Long traderId) {
        return ResponseEntity.ok(currencyService.getVisibleCurrencies(traderId));
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeTraderCurrency(@RequestParam Long traderId, @RequestParam Long currencyId) {
        traderCurrencyService.removeTraderCurrency(traderId, currencyId);
        return ResponseEntity.ok().build();
    }

}