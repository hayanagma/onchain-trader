package com.trader.ledger.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.trader.ledger.service.CurrencyService;

@Component
public class Initializer implements CommandLineRunner {

    private final CurrencyService currencyService;

    public Initializer(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void run(String... args) {
        
        currencyService.createDefaultCurrencies();

    }
}