package com.casino.ledger.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.casino.ledger.model.Currency;
import com.casino.ledger.model.CurrencyKind;
import com.casino.ledger.repository.CurrencyRepository;

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
            currencyRepository.save(trx);
        }
    }

    public Currency findCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCode(currencyCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Currency not found: " + currencyCode));
    }

}
