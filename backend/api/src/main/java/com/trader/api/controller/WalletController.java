package com.trader.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.service.WalletService;
import com.trader.shared.dto.ledger.wallet.WalletAddRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeRequest;
import com.trader.shared.dto.ledger.wallet.WalletChallengeResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/challenge")
    public Mono<WalletChallengeResponse> createChallenge(@RequestBody WalletChallengeRequest request) {
        return walletService.createChallenge(request);
    }

    @PostMapping("/verify")
    public Mono<Void> verifyAndAddWallet(@RequestBody WalletAddRequest request) {
        return walletService.verifyAndAddWallet(request);
    }

    @GetMapping
    public Mono<List<WalletTraderResponse>> getWalletsForTrader() {
        return walletService.getWalletsForCurrentTrader();
    }

}
