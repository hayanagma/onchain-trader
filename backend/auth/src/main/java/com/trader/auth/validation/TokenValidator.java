package com.trader.auth.validation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.client.IdentityClient;
import com.trader.auth.dto.AuthResult;
import com.trader.auth.security.JwtTokenProvider;

@Component
public class TokenValidator {

    private final IdentityClient identityClient;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenValidator(IdentityClient identityClient, JwtTokenProvider jwtTokenProvider) {
        this.identityClient = identityClient;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResult issueTokens(String subject, String role, int tokenVersion) {
        String accessToken = jwtTokenProvider.createAccessToken(
                subject,
                List.of(role),
                tokenVersion);
        String refreshToken = jwtTokenProvider.createRefreshToken(subject, tokenVersion);
        return new AuthResult(accessToken, refreshToken);
    }

    public String validateAndGetAdminId(String refreshToken, IdentityClient identityClient) {
        var claims = jwtTokenProvider.parseToken(refreshToken);
        String adminId = claims.getSubject();
        int tokenVersion = claims.get("tv", Integer.class);

        int actualVersion = identityClient.getTokenVersion("ROLE_ADMIN", adminId);
        if (tokenVersion != actualVersion) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin token revoked");
        }
        return adminId;
    }

    public Long validateAndGetTraderId(String refreshToken, IdentityClient identityClient) {
        var claims = jwtTokenProvider.parseToken(refreshToken);
        Long traderId = Long.valueOf(claims.getSubject());
        int tokenVersion = claims.get("tv", Integer.class);

        int actualVersion = identityClient.getTokenVersion("ROLE_TRADER", traderId.toString());
        if (tokenVersion != actualVersion) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Trader token revoked");
        }
        return traderId;
    }

    public void validate(String role, String subject, int tokenVersion) {
        int actualVersion = identityClient.getTokenVersion(role, subject);

        if (tokenVersion != actualVersion) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token revoked");
        }
    }
}
