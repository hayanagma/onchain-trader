package com.casino.ledger.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.casino.ledger.service.BankrollService;
import com.casino.ledger.service.CurrencyService;

@Component
public class Initializer implements CommandLineRunner {

    private final CurrencyService currencyService;
    private final BankrollService bankrollService;

    public Initializer(CurrencyService currencyService, BankrollService bankrollService) {
        this.currencyService = currencyService;
        this.bankrollService = bankrollService;
    }

    @Override
    public void run(String... args) {
        
        currencyService.createDefaultCurrencies();

        bankrollService.createIfMissing("TRX", new BigDecimal("25000"));
        bankrollService.createIfMissing("USDT-TRC20", new BigDecimal("20000"));
    }
}