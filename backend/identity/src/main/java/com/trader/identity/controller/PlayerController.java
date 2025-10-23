package com.trader.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.identity.service.PlayerService;
import com.trader.shared.dto.identity.player.DeleteAccountRequest;
import com.trader.shared.dto.identity.player.PlayerProfileInternalResponse;
import com.trader.shared.dto.identity.player.PlayerResponse;
import com.trader.shared.dto.identity.player.UpdateUsernameRequest;
import com.trader.shared.dto.identity.player.UsernameResponse;

@RestController
@RequestMapping("/api/internal/identity/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create")
    public ResponseEntity<PlayerResponse> createPlayer() {
        return ResponseEntity.ok(playerService.createPlayer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

        @GetMapping("/{id}/profile")
    public ResponseEntity<PlayerProfileInternalResponse> getPlayerProfile(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerProfile(id));
    }

        @PostMapping("/{id}/profile/random-username")
    public ResponseEntity<UsernameResponse> randomizeUsername(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.randomizeUsername(id));
    }

    @GetMapping("/{id}/profile/username")
    public ResponseEntity<UsernameResponse> getUsername(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getUsername(id));
    }

    @PutMapping("/{id}/profile/username")
    public ResponseEntity<Void> updateUsername(@PathVariable Long id,
            @RequestBody UpdateUsernameRequest request) {
        playerService.updateUsername(id, request);
        return ResponseEntity.ok().build();
    }

        @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deletePlayerAccount(
            @PathVariable Long id,
            @RequestBody DeleteAccountRequest request) {
        playerService.deletePlayerAccount(id, request);
        return ResponseEntity.noContent().build();
    }

}
