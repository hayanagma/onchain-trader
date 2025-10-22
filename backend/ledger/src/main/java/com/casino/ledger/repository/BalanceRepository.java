package com.casino.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.casino.ledger.model.Balance;
import com.casino.ledger.model.Wallet;

import jakarta.persistence.LockModeType;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findByWalletId(Long walletId);

    Optional<Balance> findByWalletAndCurrencyCode(Wallet wallet, String currencyCode);

    Optional<Balance> findByWallet_PlayerIdAndCurrencyCode(Long playerId, String currencyCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Balance b WHERE b.wallet.playerId = :playerId AND b.currencyCode = :currencyCode")
    Optional<Balance> findByPlayerIdAndCurrencyCodeForUpdate(Long playerId, String currencyCode);
}
