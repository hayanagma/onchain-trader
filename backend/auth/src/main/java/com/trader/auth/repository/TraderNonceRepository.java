package com.trader.auth.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.auth.model.TraderNonce;


public interface TraderNonceRepository extends JpaRepository<TraderNonce, Long> {
    Optional<TraderNonce> findByNonce(String nonce);

    void deleteByUsedTrueOrCreatedAtBefore(Instant cutoff);
}
