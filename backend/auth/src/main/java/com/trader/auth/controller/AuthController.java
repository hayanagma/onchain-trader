package com.trader.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.auth.security.JwtCookieProvider;
import com.trader.auth.security.RefreshTokenCookie;
import com.trader.auth.service.TraderAuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.*;

@RestController
@RequestMapping("/api/internal/auth")
public class AuthController {

    private final JwtCookieProvider jwtCookieProvider;
    private final TraderAuthService traderAuthService;

    public AuthController(JwtCookieProvider jwtCookieProvider, TraderAuthService traderAuthService) {
        this.jwtCookieProvider = jwtCookieProvider;
        this.traderAuthService = traderAuthService;
    }

    @PostMapping("/clear-cookies")
    public ResponseEntity<Void> clearCookies(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.TRADER);
        traderAuthService.logout(refreshToken);
        ResponseCookie cleared = jwtCookieProvider.clearRefreshCookie(RefreshTokenCookie.TRADER);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleared.toString())
                .build();
    }
}