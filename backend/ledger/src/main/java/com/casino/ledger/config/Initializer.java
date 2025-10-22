package com.casino.ledger.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.casino.ledger.service.CurrencyService;

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