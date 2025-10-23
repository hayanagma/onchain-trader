package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.AuthClient;
import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.WalletClient;
import com.trader.api.security.PlayerContext;
import com.trader.shared.dto.identity.admin.AdminPlayerResponse;
import com.trader.shared.dto.identity.player.DeleteAccountRequest;
import com.trader.shared.dto.identity.player.PlayerProfileResponse;
import com.trader.shared.dto.identity.player.UpdateUsernameRequest;
import com.trader.shared.dto.identity.player.UsernameResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {

    private final IdentityClient identityClient;
    private final WalletClient walletClient;
    private final PlayerContext playerContext;
    private final AuthClient authClient;

    public PlayerService(IdentityClient identityClient, WalletClient walletClient, PlayerContext playerContext,
            AuthClient authClient) {
        this.identityClient = identityClient;
        this.walletClient = walletClient;
        this.playerContext = playerContext;
        this.authClient = authClient;
    }

    public Mono<PlayerProfileResponse> getPlayerProfile() {
        Long playerId = playerContext.getCurrentPlayerId();

        return identityClient.getPlayerProfile(playerId)
                .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                        .map(wallet -> new PlayerProfileResponse(
                                player.getUsername(),
                                wallet,
                                player.getUsernameChangeStatus())));
    }
    

    public Flux<AdminPlayerResponse> getPlayers(String walletAddress) {
        if (walletAddress != null && !walletAddress.isBlank()) {
            return walletClient.getPlayerIdByWalletAddress(walletAddress)
                    .flatMapMany(playerId -> {
                        if (playerId == null) {
                            return Flux.empty();
                        }
                        return identityClient.getPlayer(playerId)
                                .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                                        .map(wallet -> new AdminPlayerResponse(
                                                player.getId(),
                                                player.getUsername(),
                                                player.isBanned(),
                                                player.getBannedReason(),
                                                player.isActive(),
                                                wallet)))
                                .flux();
                    });
        } else {
            return identityClient.getPlayers()
                    .flatMap(player -> walletClient.getWalletForPlayer(player.getId())
                            .map(wallet -> new AdminPlayerResponse(
                                    player.getId(),
                                    player.getUsername(),
                                    player.isBanned(),
                                    player.getBannedReason(),
                                    player.isActive(),
                                    wallet)));
        }
    }


    public Mono<UsernameResponse> randomizeUsername() {
        Long playerId = playerContext.getCurrentPlayerId();
        return identityClient.randomizeUsername(playerId);
    }

    public Mono<UsernameResponse> getUsername() {
        Long playerId = playerContext.getCurrentPlayerId();
        return identityClient.getUsername(playerId);
    }

    public Mono<Void> updateUsername(UpdateUsernameRequest request) {
        Long playerId = playerContext.getCurrentPlayerId();
        return identityClient.updateUsername(playerId, request);
    }
  

    public Mono<Void> deleteAccount(DeleteAccountRequest request) {
        Long playerId = playerContext.getCurrentPlayerId();

        return authClient.clearPlayerCookies()
                .then(walletClient.cleanupPlayerWallet(playerId))
                .then(identityClient.deletePlayerAccount(playerId, request));
    }
 
}
