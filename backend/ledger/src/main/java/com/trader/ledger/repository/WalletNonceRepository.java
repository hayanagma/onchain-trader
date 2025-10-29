package com.trader.ledger.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.WalletNonce;

@Repository
public interface WalletNonceRepository extends JpaRepository<WalletNonce, Long> {

    Optional<WalletNonce> findByNonce(String nonce);

    Optional<WalletNonce> findByWalletAddressAndNetworkAndUsedFalse(String walletAddress, String network);

    void deleteByUsedTrueOrCreatedAtBefore(Instant cutoff);
}