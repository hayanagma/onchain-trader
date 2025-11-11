package com.trader.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.trader.auth.client.IdentityClient;
import com.trader.auth.client.MailClient;
import com.trader.auth.dto.AuthResult;
import com.trader.auth.dto.admin.AdminLoginRequest;
import com.trader.auth.dto.admin.AdminVerifyRequest;
import com.trader.auth.util.TwoFactorUtil;
import com.trader.auth.validation.TokenValidator;

import java.time.Duration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.JwtException;

import org.springframework.security.core.Authentication;

@Service
public class AdminAuthService {

    private final AuthenticationManager authenticationManager;
    private final IdentityClient identityClient;
    private final TokenValidator tokenValidator;
    private final TwoFactorUtil twoFactorUtil;
    private final MailClient mailClient;

    public AdminAuthService(
            AuthenticationManager authenticationManager,
            IdentityClient identityClient, TokenValidator tokenValidator, TwoFactorUtil twoFactorUtil,
            MailClient mailClient) {
        this.authenticationManager = authenticationManager;
        this.identityClient = identityClient;
        this.tokenValidator = tokenValidator;
        this.twoFactorUtil = twoFactorUtil;
        this.mailClient = mailClient;
    }

    public AuthResult login(AdminLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String adminId = ((UserDetails) authentication.getPrincipal()).getUsername();

        String mail = identityClient.getAdminMail(adminId);

        String code = twoFactorUtil.generateCode(adminId, Duration.ofMinutes(5));
        mailClient.sendAdmin2AuthMail(mail, code);

        return AuthResult.pending();
    }

    public AuthResult verify(AdminVerifyRequest request) {
        if (!twoFactorUtil.verifyCode(request.getAdminId(), request.getCode())) {
            throw new BadCredentialsException("Invalid or expired 2FA code");
        }

        int tokenVersion = identityClient.getTokenVersion("ROLE_ADMIN", request.getAdminId());
        return tokenValidator.issueTokens(request.getAdminId(), "ROLE_ADMIN", tokenVersion);
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
