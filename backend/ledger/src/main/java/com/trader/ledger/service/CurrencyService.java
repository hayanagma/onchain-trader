package com.trader.ledger.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Currency;
import com.trader.ledger.repository.CurrencyRepository;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.CurrencyKind;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void createDefaultCurrencies() {
        if (currencyRepository.findById("USDT-TRC20").isEmpty()) {
            Currency usdt = new Currency();
            usdt.setCode("USDT-TRC20");
            usdt.setNetwork("TRON");
            usdt.setDecimals(6);
            usdt.setEnabled(true);
            usdt.setKind(CurrencyKind.TOKEN);
            usdt.setContractAddress("TXLAQ63Xg1NAzckPwKHvzw7CSEmLMEqcdj");
            usdt.setPlayerId(null);
            currencyRepository.save(usdt);
        }

        if (currencyRepository.findById("TRX").isEmpty()) {
            Currency trx = new Currency();
            trx.setCode("TRX");
            trx.setNetwork("TRON");
            trx.setDecimals(6);
            trx.setEnabled(true);
            trx.setKind(CurrencyKind.NATIVE);
            trx.setContractAddress(null);
            trx.setPlayerId(null);
            currencyRepository.save(trx);
        }
    }

    public Currency findCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCode(currencyCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Currency not found: " + currencyCode));
    }

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currency -> new CurrencyResponse(
                        currency.getCode(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.isEnabled(),
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }

}
