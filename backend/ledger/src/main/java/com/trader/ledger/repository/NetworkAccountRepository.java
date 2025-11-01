package com.trader.ledger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.NetworkAccount;
import com.trader.shared.enums.NetworkType;

@Repository
public interface NetworkAccountRepository extends JpaRepository<NetworkAccount, Long> {

    boolean existsByTraderIdAndNetwork(Long traderId, NetworkType network);

    List<NetworkAccount> findByTraderId(Long traderId);
}