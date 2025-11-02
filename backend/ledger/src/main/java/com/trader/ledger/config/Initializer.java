package com.trader.ledger.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.trader.ledger.service.CurrencyService;
import com.trader.ledger.service.PaymentCurrencyService;

@Component
public class Initializer implements CommandLineRunner {

    private final CurrencyService currencyService;
    private final PaymentCurrencyService paymentCurrencyService;

    public Initializer(CurrencyService currencyService, PaymentCurrencyService paymentCurrencyService) {
        this.currencyService = currencyService;
        this.paymentCurrencyService = paymentCurrencyService;
    }

    @Override
    public void run(String... args) {
        currencyService.createDefaultCurrencies();
        paymentCurrencyService.createDefaultPaymentCurrencies();
    }
}