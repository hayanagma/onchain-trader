package com.casino.identity.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casino.identity.dto.PlayerResponse;
import com.casino.identity.model.Player;
import com.casino.identity.repository.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final RandomNameGenerator randomNameGenerator;

    public PlayerService(PlayerRepository playerRepository, RandomNameGenerator randomNameGenerator) {
        this.playerRepository = playerRepository;
        this.randomNameGenerator = randomNameGenerator;
    }

    public PlayerResponse getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + playerId));
        return toResponse(player);
    }

    public PlayerResponse createPlayer() {
        Player player = new Player();
        player.setUsername(randomNameGenerator.generate());
        player.setBanned(false);
        player.setTokenVersion(1);
        Player saved = playerRepository.save(player);
        return toResponse(saved);
    }

    private PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.isBanned(),
                player.getBannedReason(),
                player.getTokenVersion());
    }

    public int getTokenVersion(Long playerId) {
        return getPlayerById(playerId).getTokenVersion();
    }

    public void bumpTokenVersion(Long playerId) {
        playerRepository.bumpTokenVersionById(playerId);
    }
}