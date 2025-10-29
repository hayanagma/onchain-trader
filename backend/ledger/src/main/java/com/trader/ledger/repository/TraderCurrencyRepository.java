package com.trader.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.ledger.model.TraderCurrency;


public interface TraderCurrencyRepository extends JpaRepository<TraderCurrency, Long> {
    boolean existsByTraderIdAndCurrencyId(Long traderId, Long currencyId);
}