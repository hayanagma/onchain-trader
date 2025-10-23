package com.trader.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import reactor.core.publisher.Mono;
/* 
@RestController
@RequestMapping("/api/player/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public Mono<ResponseEntity<WalletWithBalancesResponse>> getWallet() {
        return walletService.getWallet()
                .map(ResponseEntity::ok);
    }

} */