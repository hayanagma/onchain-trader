package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.PlayerContext;
import com.trader.shared.dto.identity.player.PlayerProfileResponse;

import reactor.core.publisher.Mono;

@Service
public class WalletService {

    private final IdentityClient identityClient;
    private final WalletClient walletClient;
    private final PlayerContext playerContext;

    public WalletService(IdentityClient identityClient, WalletClient walletClient, PlayerContext playerContext) {
        this.identityClient = identityClient;
        this.walletClient = walletClient;
        this.playerContext = playerContext;
    }

    
    public Mono<PlayerProfileResponse> createPlayerWallet() {
        Long playerId = playerContext.getCurrentPlayerId();

        //
        return identityClient.getPlayerProfile(playerId)
                .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                        .map(wallet -> new PlayerProfileResponse(
                                player.getUsername(),
                                wallet,
                                player.getUsernameChangeStatus())));
    }

    public Mono<PlayerProfileResponse> getPlayerWallets() {
        Long playerId = playerContext.getCurrentPlayerId();

        return identityClient.getPlayerProfile(playerId)
                .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                        .map(wallet -> new PlayerProfileResponse(
                                player.getUsername(),
                                wallet,
                                player.getUsernameChangeStatus())));
    }


    public Mono<PlayerProfileResponse> deletePlayerWallet() {
        Long playerId = playerContext.getCurrentPlayerId();

        return identityClient.getPlayerProfile(playerId)
                .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                        .map(wallet -> new PlayerProfileResponse(
                                player.getUsername(),
                                wallet,
                                player.getUsernameChangeStatus())));
    }

}
