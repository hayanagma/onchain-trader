package com.trader.ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.WalletService;
import com.trader.ledger.validation.WalletValidator;
import com.trader.shared.dto.ledger.wallet.WalletEnsureRequest;
import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;
import com.trader.shared.dto.ledger.wallet.WalletResponse;
import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;

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

    @GetMapping("/wallet-by-player")
    public ResponseEntity<WalletPlayerResponse> getWalletForPlayer(@RequestParam Long playerId) {
        return ResponseEntity.ok(walletService.getWalletWithBalancesByPlayerId(playerId));
    }

    @GetMapping("/resolve-player")
    public ResponseEntity<Long> getPlayerIdByWalletAddress(@RequestParam String address) {
        return ResponseEntity.ok(walletService.findPlayerIdByWalletAddress(address));
    }

}