package com.trader.identity.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.model.Player;
import com.trader.identity.repository.PlayerRepository;
import com.trader.identity.validation.PlayerValidator;
import com.trader.shared.dto.identity.player.PlayerProfileInternalResponse;
import com.trader.shared.dto.identity.player.PlayerResponse;
import com.trader.shared.dto.identity.player.UsernameChangeStatus;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final RandomNameGenerator randomNameGenerator;
    private final PlayerValidator playerValidator;

    public PlayerService(PlayerRepository playerRepository, RandomNameGenerator randomNameGenerator, PlayerValidator playerValidator) {
        this.playerRepository = playerRepository;
        this.randomNameGenerator = randomNameGenerator;
        this.playerValidator = playerValidator;
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

    public PlayerProfileInternalResponse getPlayerProfile(Long playerId) {
        Player player = getPlayerEntity(playerId);

        UsernameChangeStatus status = playerValidator.getUsernameChangeStatus(player);

        return new PlayerProfileInternalResponse(player.getId(), player.getUsername(), status);
    }

    public Player getPlayerEntity(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Player not found: " + playerId));
    }

}