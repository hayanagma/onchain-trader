package com.casino.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casino.auth.dto.AuthResult;
import com.casino.auth.dto.LoginResponse;
import com.casino.auth.dto.admin.AdminLoginRequest;
import com.casino.auth.security.JwtCookieProvider;
import com.casino.auth.security.RefreshTokenCookie;
import com.casino.auth.service.AdminAuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;
    private final JwtCookieProvider jwtCookieProvider;

    public AdminAuthController(AdminAuthService adminAuthService, JwtCookieProvider jwtCookieProvider) {
        this.adminAuthService = adminAuthService;
        this.jwtCookieProvider = jwtCookieProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AdminLoginRequest request) {
        AuthResult result = adminAuthService.login(request);

        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.ADMIN);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.ADMIN);
        AuthResult result = adminAuthService.refresh(refreshToken);
        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.ADMIN);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.ADMIN);
        adminAuthService.logout(refreshToken);
        ResponseCookie cleared = jwtCookieProvider.clearRefreshCookie(RefreshTokenCookie.ADMIN);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleared.toString())
                .build();
    }
}