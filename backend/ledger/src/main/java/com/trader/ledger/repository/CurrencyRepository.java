package com.trader.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.ledger.model.Currency;
import com.trader.shared.enums.NetworkType;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByNetwork(NetworkType network);

    Optional<Currency> findByCode(String code);

    Optional<Currency> findByCodeAndNetworkAndPlayerId(String code, NetworkType network, Long playerId);

}