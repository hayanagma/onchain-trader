package com.trader.ledger.validation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Currency;
import com.trader.ledger.repository.CurrencyRepository;

@Component
public class CurrencyValidator {

    private final CurrencyRepository currencyRepository;

    public CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> validateCurrenciesForNetwork(String network) {
        List<Currency> currencies = currencyRepository.findByNetwork(network);
        if (currencies.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No enabled currencies for network: " + network);
        }
        return currencies;
    }
}
