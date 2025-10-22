package com.casino.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import org.springframework.security.authentication.AuthenticationManager;

import com.casino.auth.client.IdentityClient;
import com.casino.auth.dto.AuthResult;
import com.casino.auth.dto.admin.AdminLoginRequest;
import com.casino.auth.validation.TokenValidator;

import io.jsonwebtoken.JwtException;

import org.springframework.security.core.Authentication;

@Service
public class AdminAuthService {

    private final AuthenticationManager authenticationManager;
    private final IdentityClient identityClient;
    private final TokenValidator tokenValidator;

    public AdminAuthService(
            AuthenticationManager authenticationManager,
            IdentityClient identityClient, TokenValidator tokenValidator) {
        this.authenticationManager = authenticationManager;
        this.identityClient = identityClient;
        this.tokenValidator = tokenValidator;
    }

    public AuthResult login(AdminLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String adminId = ((UserDetails) authentication.getPrincipal()).getUsername();
        int tokenVersion = identityClient.getTokenVersion("ROLE_ADMIN", adminId);

        return tokenValidator.issueTokens(adminId, "ROLE_ADMIN", tokenVersion);
    }

    public AuthResult refresh(String refreshToken) {
        String adminId = tokenValidator.validateAndGetAdminId(refreshToken, identityClient);
        int tokenVersion = identityClient.getTokenVersion("ROLE_ADMIN", adminId);
        return tokenValidator.issueTokens(adminId, "ROLE_ADMIN", tokenVersion);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null) {
            return;
        }
        try {
            String adminId = tokenValidator.validateAndGetAdminId(refreshToken, identityClient);
            identityClient.bumpTokenVersion("ROLE_ADMIN", adminId);
        } catch (JwtException e) {
            // ignore invalid token
        }
    }

}
