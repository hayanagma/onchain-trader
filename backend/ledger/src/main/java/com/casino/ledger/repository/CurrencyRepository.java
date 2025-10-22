package com.casino.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casino.ledger.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    List<Currency> findByNetwork(String network);

    Optional<Currency> findByCode(String code);
}