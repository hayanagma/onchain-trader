package com.casino.ledger.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.casino.ledger.model.Bankroll;
import com.casino.ledger.model.Currency;
import com.casino.ledger.repository.BankrollRepository;

@Service
public class BankrollService {

    private final BankrollRepository bankrollRepository;
    private final CurrencyService currencyService;

    public BankrollService(BankrollRepository bankrollRepository, CurrencyService currencyService) {
        this.bankrollRepository = bankrollRepository;
        this.currencyService = currencyService;
    }

    public void createIfMissing(String currencyCode, BigDecimal initialAmount) {
        if (!bankrollRepository.existsByCurrency_Code(currencyCode)) {

            Currency currency = currencyService.findCurrencyByCode(currencyCode);

            Bankroll bankroll = new Bankroll();
            bankroll.setCurrency(currency);
            bankroll.setAmount(initialAmount);
            bankrollRepository.save(bankroll);
        }
    }

}
