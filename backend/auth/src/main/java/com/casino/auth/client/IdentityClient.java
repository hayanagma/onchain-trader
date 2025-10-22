package com.casino.auth.client;

import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.casino.auth.dto.admin.AdminResponse;
import com.casino.auth.dto.player.PlayerResponse;
import com.casino.auth.util.RestTemplateUtil;

@Service
public class IdentityClient {

    private final RestTemplate restTemplate;

    public IdentityClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .rootUri("http://identity:8083/api/internal/identity")
                .build();
    }

    public Optional<AdminResponse> findByUsername(String username) {
        try {
            AdminResponse body = RestTemplateUtil.get(
                    restTemplate,
                    "/admin/{username}",
                    AdminResponse.class,
                    username);
            return Optional.ofNullable(body);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode().value() == 404) {
                return Optional.empty();
            }
            throw ex;
        }
    }

    public PlayerResponse getPlayer(Long playerId) {
        return RestTemplateUtil.get(
                restTemplate,
                "/players/{playerId}",
                PlayerResponse.class,
                playerId);
    }

    public PlayerResponse createPlayer() {
        return RestTemplateUtil.post(
                restTemplate,
                "/players/create",
                null,
                PlayerResponse.class);
    }

    public int getTokenVersion(String role, String subject) {
        ResponseEntity<Integer> response = RestTemplateUtil.getEntity(
                restTemplate,
                "/token-version?role={role}&subject={subject}",
                Integer.class,
                role,
                subject);
        return response.getBody();
    }

    public void bumpTokenVersion(String role, String subject) {
        RestTemplateUtil.postEntity(
                restTemplate,
                "/bump-version?role={role}&subject={subject}",
                null,
                Void.class,
                role,
                subject);
    }
}