package com.trader.identity.validation;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.model.Player;
import com.trader.identity.repository.PlayerRepository;
import com.trader.shared.dto.identity.player.UsernameChangeStatus;


@Component
public class PlayerValidator {

    private final PlayerRepository playerRepository;

    public PlayerValidator(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be blank");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be 3–20 characters long");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username contains invalid characters");
        }
        if (playerRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }
    }

    public UsernameChangeStatus getUsernameChangeStatus(Player player) {
        Duration cooldown = Duration.ofDays(7);

        if (player.getLastUsernameChangeAt() == null) {
            return new UsernameChangeStatus(true, 0);
        }

        Duration sinceLastChange = Duration.between(player.getLastUsernameChangeAt(), Instant.now());
        if (sinceLastChange.compareTo(cooldown) >= 0) {
            return new UsernameChangeStatus(true, 0);
        } else {
            long remaining = cooldown.minus(sinceLastChange).toSeconds();
            return new UsernameChangeStatus(false, remaining);
        }
    }

}
