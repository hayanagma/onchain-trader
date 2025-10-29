package com.trader.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.TraderCurrency;

@Repository
public interface TraderCurrencyRepository extends JpaRepository<TraderCurrency, Long> {

    boolean existsByTraderIdAndCurrencyId(Long traderId, Long currencyId);

    void deleteAllByTraderId(Long traderId);
}