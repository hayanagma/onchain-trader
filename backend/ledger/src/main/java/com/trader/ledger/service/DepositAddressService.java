package com.trader.ledger.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DepositAddressService {

    public String generateAddress(String currencyCode, Long paymentId) {
        return switch (currencyCode.toUpperCase()) {
            case "BTC" -> "btc_" + paymentId + "_mock_address";
            case "ETH" -> "eth_" + paymentId + "_mock_address";
            case "SOL" -> "sol_" + paymentId + "_mock_address";
            case "TRX" -> "tron_" + paymentId + "_mock_address";
            case "XMR" -> "xmr_" + paymentId + "_mock_address";
            case "LTC" -> "ltc_" + paymentId + "_mock_address";
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unsupported payment currency: " + currencyCode);
        };
    }
}