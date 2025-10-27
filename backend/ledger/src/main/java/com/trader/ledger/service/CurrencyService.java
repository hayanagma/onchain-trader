package com.trader.ledger.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Currency;
import com.trader.ledger.repository.CurrencyRepository;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.NetworkType;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void createDefaultCurrencies() {
        if (currencyRepository.findByCode("TRX").isEmpty()) {
            Currency trx = new Currency();
            trx.setCode("TRX");
            trx.setNetwork(NetworkType.TRON);
            trx.setDecimals(6);
            trx.setKind(CurrencyKind.NATIVE);
            trx.setContractAddress(null);
            trx.setPlayerId(null);
            currencyRepository.save(trx);
        }

        if (currencyRepository.findByCode("ETH").isEmpty()) {
            Currency eth = new Currency();
            eth.setCode("ETH");
            eth.setNetwork(NetworkType.ETHEREUM);
            eth.setDecimals(18);
            eth.setKind(CurrencyKind.NATIVE);
            eth.setContractAddress(null);
            eth.setPlayerId(null);
            currencyRepository.save(eth);
        }

        if (currencyRepository.findByCode("SOL").isEmpty()) {
            Currency sol = new Currency();
            sol.setCode("SOL");
            sol.setNetwork(NetworkType.SOLANA);
            sol.setDecimals(9);
            sol.setKind(CurrencyKind.NATIVE);
            sol.setContractAddress(null);
            sol.setPlayerId(null);
            currencyRepository.save(sol);
        }

        if (currencyRepository.findByCode("BTC").isEmpty()) {
            Currency btc = new Currency();
            btc.setCode("BTC");
            btc.setNetwork(NetworkType.BITCOIN);
            btc.setDecimals(8);
            btc.setKind(CurrencyKind.NATIVE);
            btc.setContractAddress(null);
            btc.setPlayerId(null);
            currencyRepository.save(btc);
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
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }

    public void createCurrency(Long playerId, CurrencyAddRequest request) {
        String code = request.getCode();
        NetworkType network = request.getNetwork();
        String contractAddress = request.getContractAddress();

        currencyRepository.findByCodeAndNetworkAndPlayerId(code, network, null)
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "Currency already exists as system currency");
                });

        currencyRepository.findByCodeAndNetworkAndPlayerId(code, network, playerId)
                .ifPresent(c -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Currency already exists for player");
                });
        
        //validate contractaddress

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setNetwork(network);
        currency.setDecimals(6); // placeholder until blockchain check
        currency.setKind(CurrencyKind.TOKEN);
        currency.setContractAddress(contractAddress);
        currency.setPlayerId(playerId);

        currencyRepository.save(currency);
    }

    public List<CurrencyResponse> getCurrenciesByNetwork(NetworkType network) {
        return currencyRepository.findByNetwork(network).stream()
                .map(currency -> new CurrencyResponse(
                        currency.getCode(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }
}
