package com.casino.auth.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casino.auth.model.PlayerNonce;
import com.casino.auth.repository.PlayerNonceRepository;
import com.casino.auth.validation.NonceValidator;

import jakarta.transaction.Transactional;

@Service
public class PlayerNonceService {

    private final PlayerNonceRepository playerNonceRepository;
    private final NonceValidator nonceValidator;

    private final SecureRandom secureRandom = new SecureRandom();

    public PlayerNonceService(PlayerNonceRepository playerNonceRepository, NonceValidator nonceValidator) {
        this.playerNonceRepository = playerNonceRepository;
        this.nonceValidator = nonceValidator;
    }

    @Scheduled(fixedRate = 3600000) // every hour
    @Transactional
    public void cleanupOldNonces() {
        Instant cutoff = Instant.now().minus(15, ChronoUnit.MINUTES);
        playerNonceRepository.deleteByUsedTrueOrCreatedAtBefore(cutoff);
    }

    public String createNonce(String walletAddress, String network) {
        String nonce = generateNonce();
        PlayerNonce entity = new PlayerNonce();
        entity.setNonce(nonce);
        entity.setWalletAddress(walletAddress);
        entity.setNetwork(network);
        entity.setUsed(false);
        playerNonceRepository.save(entity);
        return nonce;
    }

    @Transactional
    public PlayerNonce consumeNonce(String nonce, String walletAddress, String network) {
        PlayerNonce entity = playerNonceRepository.findByNonce(nonce)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nonce not found"));

        nonceValidator.validate(entity, walletAddress, network);

        entity.setUsed(true);
        return playerNonceRepository.save(entity);
    }

    private String generateNonce() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
