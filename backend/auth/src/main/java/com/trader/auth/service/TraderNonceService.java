package com.trader.auth.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.model.TraderNonce;
import com.trader.auth.repository.TraderNonceRepository;
import com.trader.auth.validation.NonceValidator;

import jakarta.transaction.Transactional;

@Service
public class TraderNonceService {

    private final TraderNonceRepository traderNonceRepository;
    private final NonceValidator nonceValidator;
    private final SecureRandom secureRandom = new SecureRandom();

    public TraderNonceService(TraderNonceRepository traderNonceRepository, NonceValidator nonceValidator) {
        this.traderNonceRepository = traderNonceRepository;
        this.nonceValidator = nonceValidator;
    }

    @Scheduled(fixedRate = 3600000) // every hour
    @Transactional
    public void cleanupOldNonces() {
        Instant cutoff = Instant.now().minus(15, ChronoUnit.MINUTES);
        traderNonceRepository.deleteByUsedTrueOrCreatedAtBefore(cutoff);
    }

    public String createNonce(String walletAddress, String network) {
        String nonce = generateNonce();
        TraderNonce entity = new TraderNonce();
        entity.setNonce(nonce);
        entity.setWalletAddress(walletAddress);
        entity.setNetwork(network);
        entity.setUsed(false);
        traderNonceRepository.save(entity);
        return nonce;
    }

    @Transactional
    public TraderNonce consumeNonce(String nonce, String walletAddress, String network) {
        TraderNonce entity = traderNonceRepository.findByNonce(nonce)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nonce not found"));

        nonceValidator.validate(entity, walletAddress, network);

        entity.setUsed(true);
        return traderNonceRepository.save(entity);
    }

    private String generateNonce() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}