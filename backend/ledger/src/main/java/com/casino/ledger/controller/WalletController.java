package com.casino.ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casino.ledger.dto.WalletEnsureRequest;
import com.casino.ledger.dto.WalletResponse;
import com.casino.ledger.dto.WalletSignatureValidationRequest;
import com.casino.ledger.dto.WalletValidationRequest;
import com.casino.ledger.dto.WalletValidationResponse;
import com.casino.ledger.service.WalletService;
import com.casino.ledger.validation.WalletValidator;

@RestController
@RequestMapping("/api/internal/ledger/wallets")
public class WalletController {

    private final WalletService walletService;
    private final WalletValidator walletValidator;

    public WalletController(WalletService walletService, WalletValidator walletValidator) {
        this.walletService = walletService;
        this.walletValidator = walletValidator;
    }

    @PostMapping("/ensure")
    public ResponseEntity<Void> ensureWallet(@RequestBody WalletEnsureRequest request) {
        walletService.ensureWallet(request.getPlayerId(), request.getAddress(), request.getNetwork());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public WalletValidationResponse validate(@RequestBody WalletValidationRequest request) {
        return walletValidator.validate(request);
    }

    @PostMapping("/validate-signature")
    public WalletValidationResponse validateSignature(@RequestBody WalletSignatureValidationRequest request) {
        return walletValidator.validateSignature(request);
    }

    @GetMapping
    public ResponseEntity<WalletResponse> findByAddressAndNetwork(
            @RequestParam String address,
            @RequestParam String network) {
        return walletService.findByAddressAndNetwork(address, network)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}