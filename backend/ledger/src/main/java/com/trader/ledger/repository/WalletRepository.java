package com.trader.ledger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.ledger.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByPlayerId(Long playerId);

    Optional<Wallet> findByAddress(String address);

    Optional<Wallet> findByAddressAndNetwork(String address, String network);

}