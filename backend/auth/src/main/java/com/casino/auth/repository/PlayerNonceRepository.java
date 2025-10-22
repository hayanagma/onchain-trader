package com.casino.auth.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casino.auth.model.PlayerNonce;

public interface PlayerNonceRepository extends JpaRepository<PlayerNonce, Long> {
    Optional<PlayerNonce> findByNonce(String nonce);

    void deleteByUsedTrueOrCreatedAtBefore(Instant cutoff);
}
