package com.trader.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trader.identity.model.Trader;

import jakarta.transaction.Transactional;

public interface TraderRepository extends JpaRepository<Trader, Long> {
    boolean existsByUsername(String username);

    Optional<Trader> findByUsername(String username);

    @Query("SELECT t.tokenVersion FROM Trader t WHERE t.id = :id")
    int getTokenVersionById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Trader t SET t.tokenVersion = t.tokenVersion + 1 WHERE t.id = :id")
    void bumpTokenVersionById(@Param("id") Long id);
}
