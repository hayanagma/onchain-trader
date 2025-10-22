package com.casino.ledger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casino.ledger.model.Bankroll;

public interface BankrollRepository extends JpaRepository<Bankroll, String> {
    boolean existsByCurrency_Code(String code);

    Optional<Bankroll> findByCurrency_Code(String code);
}
