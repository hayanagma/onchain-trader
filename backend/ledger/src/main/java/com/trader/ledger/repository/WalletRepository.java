package com.trader.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.Wallet;
import com.trader.shared.enums.NetworkType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByTraderId(Long traderId);

    Optional<Wallet> findByAddress(String address);

    Optional<Wallet> findByAddressAndNetwork(String address, NetworkType network);

    List<Wallet> findAllByTraderId(Long traderId);

    Optional<Wallet> findByIdAndTraderId(Long id, Long traderId);

}