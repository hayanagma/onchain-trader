package com.trader.ledger.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Currency;
import com.trader.ledger.model.TraderCurrency;
import com.trader.ledger.repository.TraderCurrencyRepository;

import jakarta.transaction.Transactional;

@Service
public class TraderCurrencyService {

    private final TraderCurrencyRepository traderCurrencyRepository;

    public TraderCurrencyService(TraderCurrencyRepository traderCurrencyRepository) {
        this.traderCurrencyRepository = traderCurrencyRepository;
    }

    public void linkTraderToCurrency(Long traderId, Currency currency) {
        if (traderCurrencyRepository.existsByTraderIdAndCurrencyId(traderId, currency.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Trader already added this token");
        }

        TraderCurrency traderCurrency = new TraderCurrency();
        traderCurrency.setTraderId(traderId);
        traderCurrency.setCurrency(currency);
        traderCurrencyRepository.save(traderCurrency);
    }

    public void removeAllForTrader(Long traderId) {
        traderCurrencyRepository.deleteAllByTraderId(traderId);
    }

    @Transactional
    public void removeTraderCurrency(Long traderId, Long currencyId) {
        traderCurrencyRepository.deleteByTraderIdAndCurrencyId(traderId, currencyId);
    }
}