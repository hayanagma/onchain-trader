package com.trader.api.service;

import org.springframework.stereotype.Service;


import com.trader.api.security.PlayerContext;

import reactor.core.publisher.Mono;
/* 
@Service
public class WalletService {

    private final PlayerContext playerContext;
    private final WalletClient walletClient;

    public WalletService(PlayerContext playerContext, WalletClient walletClient) {
        this.playerContext = playerContext;
        this.walletClient = walletClient;
    }

    public Mono<WalletWithBalancesResponse> getWallet() {
        Long playerId = playerContext.getCurrentPlayerId();
        return walletClient.getWalletForPlayer(playerId);
    }
}
 */