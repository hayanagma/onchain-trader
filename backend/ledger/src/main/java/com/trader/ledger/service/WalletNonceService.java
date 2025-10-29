package com.trader.ledger.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.WalletNonce;
import com.trader.ledger.repository.WalletNonceRepository;
import com.trader.ledger.validation.WalletNonceValidator;
import com.trader.ledger.validation.WalletValidator;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;
import com.trader.shared.enums.NetworkType;

import jakarta.transaction.Transactional;

@Service
public class WalletNonceService {

    private final WalletNonceRepository walletNonceRepository;
    private final WalletNonceValidator nonceValidator;
    private final SecureRandom secureRandom = new SecureRandom();
    private final WalletValidator walletValidator;
    private final WalletService walletService;

    public WalletNonceService(WalletNonceRepository walletNonceRepository, WalletNonceValidator nonceValidator,
            WalletValidator walletValidator, WalletService walletService) {
        this.walletNonceRepository = walletNonceRepository;
        this.nonceValidator = nonceValidator;
        this.walletValidator = walletValidator;
        this.walletService = walletService;
    }

    public WalletChallengeResponse createChallenge(Long traderId, WalletChallengeRequest request) {
        WalletValidationResponse validated = walletValidator.validate(
                new WalletValidationRequest(request.getAddress(), request.getNetwork()));

        String nonce = createNonce(validated.getAddress(), validated.getNetwork());

        return new WalletChallengeResponse(nonce);
    }

    @Transactional
    public void verifyAndAddWallet(Long traderId, WalletAddRequest request) {
        WalletNonce nonceEntity = walletNonceRepository.findByNonce(request.getNonce())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nonce not found"));

        nonceValidator.validate(nonceEntity, request.getAddress(), request.getNetwork());

        walletValidator.validateSignature(
                new WalletSignatureValidationRequest(
                        request.getAddress(),
                        request.getNetwork(),
                        nonceEntity.getNonce(),
                        request.getSignature()));

        nonceEntity.setUsed(true);
        walletNonceRepository.save(nonceEntity);

        walletService.createWallet(traderId, request.getAddress(), request.getNetwork());
    }

    @Scheduled(fixedRate = 3600000) // every hour
    @Transactional
    public void cleanupOldNonces() {
        Instant cutoff = Instant.now().minus(15, ChronoUnit.MINUTES);
        walletNonceRepository.deleteByUsedTrueOrCreatedAtBefore(cutoff);
    }

    public String createNonce(String walletAddress, NetworkType network) {
        String nonce = generateNonce();
        WalletNonce entity = new WalletNonce();
        entity.setNonce(nonce);
        entity.setWalletAddress(walletAddress);
        entity.setNetwork(network);
        entity.setUsed(false);
        walletNonceRepository.save(entity);
        return nonce;
    }

    @Transactional
    public WalletNonce consumeNonce(String nonce, String walletAddress, NetworkType network) {
        WalletNonce entity = walletNonceRepository.findByNonce(nonce)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nonce not found"));

        nonceValidator.validate(entity, walletAddress, network);

        entity.setUsed(true);
        return walletNonceRepository.save(entity);
    }

    private String generateNonce() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}