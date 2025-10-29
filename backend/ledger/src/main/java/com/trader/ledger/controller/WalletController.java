package com.trader.ledger.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.WalletNonceService;
import com.trader.ledger.service.WalletService;
import com.trader.ledger.validation.WalletValidator;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletEnsureRequest;
import com.trader.shared.dto.ledger.wallet.WalletResponse;
import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;
import com.trader.shared.enums.NetworkType;

@RestController
@RequestMapping("/api/internal/ledger/wallets")
public class WalletController {

    private final WalletService walletService;
    private final WalletValidator walletValidator;
    private final WalletNonceService walletNonceService;

    public WalletController(WalletService walletService, WalletValidator walletValidator,
            WalletNonceService walletNonceService) {
        this.walletService = walletService;
        this.walletValidator = walletValidator;
        this.walletNonceService = walletNonceService;
    }

    @PostMapping("/ensure")
    public ResponseEntity<Void> ensureWallet(@RequestBody WalletEnsureRequest request) {
        walletService.ensureWallet(request.getTraderId(), request.getAddress(), request.getNetwork());
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
            @RequestParam NetworkType network) {
        return walletService.findByAddressAndNetwork(address, network)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/wallet-by-trader")
    public ResponseEntity<WalletTraderResponse> getWalletForTrader(@RequestParam Long traderId) {
        return ResponseEntity.ok(walletService.getWalletByTraderId(traderId));
    }

    @GetMapping("/by-trader")
    public ResponseEntity<List<WalletTraderResponse>> getWalletsByTrader(@RequestParam Long traderId) {
        List<WalletTraderResponse> wallets = walletService.getWalletsByTraderId(traderId);
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/resolve-trader")
    public ResponseEntity<Long> getTraderIdByWalletAddress(@RequestParam String address) {
        return ResponseEntity.ok(walletService.findTraderIdByWalletAddress(address));
    }

    @PostMapping("/{traderId}/cleanup")
    public ResponseEntity<Void> cleanupTraderWallet(@PathVariable Long traderId) {
        walletService.cleanupTraderWallet(traderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/challenge")
    public ResponseEntity<WalletChallengeResponse> createChallenge(
            @RequestParam("traderId") Long traderId,
            @RequestBody WalletChallengeRequest request) {
        WalletChallengeResponse response = walletNonceService.createChallenge(traderId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyAndAddWallet(
            @RequestParam("traderId") Long traderId,
            @RequestBody WalletAddRequest request) {
        walletNonceService.verifyAndAddWallet(traderId, request);
        return ResponseEntity.ok().build();
    }
}