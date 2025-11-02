package com.trader.ledger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.PaymentCurrency;
import com.trader.ledger.repository.PaymentCurrencyRepository;
import com.trader.shared.dto.ledger.paymentcurrency.PaymentCurrencyResponse;
import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.PaymentNetworkType;

@Service
public class PaymentCurrencyService {

    private final PaymentCurrencyRepository paymentCurrencyRepository;

    public PaymentCurrencyService(PaymentCurrencyRepository paymentCurrencyRepository) {
        this.paymentCurrencyRepository = paymentCurrencyRepository;
    }

    public void createDefaultPaymentCurrencies() {
        upsert("BTC", "Bitcoin", PaymentNetworkType.BITCOIN, 8);
        upsert("ETH", "Ethereum", PaymentNetworkType.ETHEREUM, 18);
        upsert("SOL", "Solana", PaymentNetworkType.SOLANA, 9);
        upsert("XMR", "Monero", PaymentNetworkType.MONERO, 12);
        upsert("TRX", "TRON", PaymentNetworkType.TRON, 6);
        upsert("LTC", "Litecoin", PaymentNetworkType.LITECOIN, 8);
    }

    private void upsert(String code, String name, PaymentNetworkType network, int decimals) {
        Optional<PaymentCurrency> existing = paymentCurrencyRepository.findByCodeAndNetwork(code, network);
        if (existing.isEmpty()) {
            PaymentCurrency currency = new PaymentCurrency();
            currency.setCode(code);
            currency.setName(name);
            currency.setNetwork(network);
            currency.setDecimals(decimals);
            currency.setKind(CurrencyKind.NATIVE);
            currency.setContractAddress(null);
            currency.setEnabled(true);
            paymentCurrencyRepository.save(currency);
        }
    }

    public PaymentCurrency validatePaymentCurrency(String code, PaymentNetworkType network) {

        PaymentCurrency currency = paymentCurrencyRepository
                .findByCodeAndNetwork(
                        code,
                        network)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unsupported or invalid payment currency: "
                                + code
                                + " on network: "
                                + network));

        if (!currency.isEnabled()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Currency is currently disabled: " + currency.getCode());
        }

        return currency;
    }

    public List<PaymentCurrencyResponse> getAllPaymentCurrencies() {
        return paymentCurrencyRepository.findAll()
                .stream()
                .map(currency -> new PaymentCurrencyResponse(
                        currency.getId(),
                        currency.getCode(),
                        currency.getName(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.getKind(),
                        currency.getContractAddress(),
                        currency.isEnabled()))
                .toList();
    }
}