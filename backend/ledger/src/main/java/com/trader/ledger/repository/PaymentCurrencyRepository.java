package com.trader.ledger.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.ledger.model.PaymentCurrency;
import com.trader.shared.enums.PaymentNetworkType;

public interface PaymentCurrencyRepository extends JpaRepository<PaymentCurrency, Long> {
    Optional<PaymentCurrency> findByCodeAndNetwork(String code, PaymentNetworkType network);
}