package com.trader.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.ledger.model.PlayerCurrency;

public interface PlayerCurrencyRepository extends JpaRepository<PlayerCurrency, Long> {
    boolean existsByPlayerIdAndCurrency_Id(Long playerId, Long currencyId);

}
