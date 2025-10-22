package com.casino.ledger.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import com.casino.ledger.model.Balance;
import com.casino.ledger.model.Currency;
import com.casino.ledger.model.Wallet;
import com.casino.ledger.repository.BalanceRepository;
import com.casino.ledger.validation.CurrencyValidator;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final CurrencyValidator currencyValidator;

    public BalanceService(BalanceRepository balanceRepository, CurrencyValidator currencyValidator) {
        this.balanceRepository = balanceRepository;
        this.currencyValidator = currencyValidator;
    }

    public void createBalancesForWallet(Wallet wallet, String network) {
        List<Currency> currencies = currencyValidator.validateCurrenciesForNetwork(network);
        for (Currency currency : currencies) {
            createBalance(wallet, currency.getCode());
        }
    }

    public Balance createBalance(Wallet wallet, String currencyCode) {
        Balance balance = new Balance();
        balance.setWallet(wallet);
        balance.setCurrencyCode(currencyCode);
        balance.setAmount(BigDecimal.ZERO);

        return balanceRepository.save(balance);
    }

}
