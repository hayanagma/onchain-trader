package com.trader.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.auth.security.JwtCookieProvider;
import com.trader.auth.security.RefreshTokenCookie;
import com.trader.auth.service.PlayerAuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.*;

@RestController
@RequestMapping("/api/internal/auth")
public class AuthController {

    private final JwtCookieProvider jwtCookieProvider;
    private final PlayerAuthService playerAuthService;

    public AuthController(JwtCookieProvider jwtCookieProvider, PlayerAuthService playerAuthService) {
        this.jwtCookieProvider = jwtCookieProvider;
        this.playerAuthService = playerAuthService;
    }

    @PostMapping("/clear-cookies")
    public ResponseEntity<Void> clearCookies(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.PLAYER);
        playerAuthService.logout(refreshToken);
        ResponseCookie cleared = jwtCookieProvider.clearRefreshCookie(RefreshTokenCookie.PLAYER);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleared.toString())
                .build();
    }
}