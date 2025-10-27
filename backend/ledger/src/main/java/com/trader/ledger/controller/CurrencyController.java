package com.trader.ledger.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.CurrencyService;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.NetworkType;


@RestController
@RequestMapping("/api/internal/ledger/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCurrency(@RequestParam Long playerId,
            @RequestBody CurrencyAddRequest request) {
        currencyService.createCurrency(playerId, request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/network/{network}")
    public ResponseEntity<List<CurrencyResponse>> getCurrenciesByNetwork(@PathVariable NetworkType network) {
        return ResponseEntity.ok(currencyService.getCurrenciesByNetwork(network));
    }
}
