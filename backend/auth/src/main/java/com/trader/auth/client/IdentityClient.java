package com.trader.auth.client;

import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.trader.auth.util.RestTemplateUtil;
import com.trader.shared.dto.identity.admin.AdminResponse;
import com.trader.shared.dto.identity.trader.TraderResponse;

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

    public TraderResponse getTrader(Long traderId) {
        return RestTemplateUtil.get(
                restTemplate,
                "/traders/{traderId}",
                TraderResponse.class,
                traderId);
    }

    public TraderResponse createTrader() {
        return RestTemplateUtil.post(
                restTemplate,
                "/traders/create",
                null,
                TraderResponse.class);
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