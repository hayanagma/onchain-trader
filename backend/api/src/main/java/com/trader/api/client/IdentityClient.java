package com.trader.api.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import com.trader.api.util.WebClientUtil;
import com.trader.shared.dto.identity.admin.AdminPlayerInternalResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.identity.player.DeleteAccountRequest;
import com.trader.shared.dto.identity.player.PlayerProfileInternalResponse;
import com.trader.shared.dto.identity.player.UpdateUsernameRequest;
import com.trader.shared.dto.identity.player.UsernameResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Service
public class IdentityClient {

        private final WebClient webClient;

        public IdentityClient(WebClient.Builder builder) {
                this.webClient = builder
                                .baseUrl("http://identity:8083/api/internal/identity")
                                .build();
        }

        public Mono<PlayerProfileInternalResponse> getPlayerProfile(Long playerId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri("/players/{id}/profile", playerId)
                                                .retrieve(),
                                PlayerProfileInternalResponse.class);
        }

        public Mono<AdminPlayerInternalResponse> getPlayer(Long playerId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                                .path("/admin/players/{id}")
                                                                .build(playerId))
                                                .retrieve(),
                                AdminPlayerInternalResponse.class);
        }

        public Flux<AdminPlayerInternalResponse> getPlayers() {
                return WebClientUtil.handleFlux(
                                webClient.get()
                                                .uri("/admin/players")
                                                .retrieve(),
                                AdminPlayerInternalResponse.class);
        }
        
        public Mono<Void> updateBanStatus(BanRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.put()
                                                .uri("/admin/players/ban-status")
                                                .bodyValue(request)
                                                .retrieve());
        }
        

        public Mono<UsernameResponse> randomizeUsername(Long playerId) {
                return WebClientUtil.handle(
                                webClient.post()
                                                .uri("/players/{id}/profile/random-username", playerId)
                                                .retrieve(),
                                UsernameResponse.class);
        }

        public Mono<UsernameResponse> getUsername(Long playerId) {
                return WebClientUtil.handle(
                                webClient.get()
                                                .uri("/players/{id}/profile/username", playerId)
                                                .retrieve(),
                                UsernameResponse.class);
        }

        public Mono<Void> updateUsername(Long playerId, UpdateUsernameRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.put()
                                                .uri("/players/{id}/profile/username", playerId)
                                                .bodyValue(request)
                                                .retrieve());
        }


        public Mono<Void> deletePlayerAccount(Long playerId, DeleteAccountRequest request) {
                return WebClientUtil.handleVoid(
                                webClient.post()
                                                .uri("/players/{id}/delete", playerId)
                                                .bodyValue(request)
                                                .retrieve());
        }
        
}
 