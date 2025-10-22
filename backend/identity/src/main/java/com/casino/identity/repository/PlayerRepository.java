package com.casino.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.casino.identity.model.Player;

import jakarta.transaction.Transactional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByUsername(String username);

    Optional<Player> findByUsername(String username);

    @Query("SELECT p.tokenVersion FROM Player p WHERE p.id = :id")
    int getTokenVersionById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Player p SET p.tokenVersion = p.tokenVersion + 1 WHERE p.id = :id")
    void bumpTokenVersionById(@Param("id") Long id);
}
