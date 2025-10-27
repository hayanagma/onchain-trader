package com.trader.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.service.PlayerService;
import com.trader.shared.dto.identity.player.DeleteAccountRequest;
import com.trader.shared.dto.identity.player.PlayerProfileResponse;
import com.trader.shared.dto.identity.player.UpdateUsernameRequest;
import com.trader.shared.dto.identity.player.UsernameResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public Mono<ResponseEntity<PlayerProfileResponse>> getPlayerProfile() {
        return playerService.getPlayerProfile()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/profile/random-username")
    public Mono<ResponseEntity<UsernameResponse>> randomizeUsername() {
        return playerService.randomizeUsername()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/profile/username")
    public Mono<ResponseEntity<UsernameResponse>> getUsername() {
        return playerService.getUsername()
                .map(ResponseEntity::ok);
    }

    @PutMapping("/profile/username")
    public Mono<ResponseEntity<Void>> updateUsername(@RequestBody UpdateUsernameRequest request) {
        return playerService.updateUsername(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/account/delete")
    public Mono<ResponseEntity<Void>> deleteAccount(@RequestBody DeleteAccountRequest request) {
        return playerService.deleteAccount(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }



}
