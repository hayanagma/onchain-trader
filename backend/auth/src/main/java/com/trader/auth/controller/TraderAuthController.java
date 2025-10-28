package com.trader.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.auth.dto.AuthResult;
import com.trader.auth.dto.LoginResponse;
import com.trader.auth.dto.player.ChallengeRequest;
import com.trader.auth.dto.player.ChallengeResponse;
import com.trader.auth.dto.player.TraderLoginRequest;
import com.trader.auth.security.JwtCookieProvider;
import com.trader.auth.security.RefreshTokenCookie;
import com.trader.auth.service.TraderAuthService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/auth/trader")
public class TraderAuthController {

    private final TraderAuthService traderAuthService;
    private final JwtCookieProvider jwtCookieProvider;

    public TraderAuthController(TraderAuthService traderAuthService, JwtCookieProvider jwtCookieProvider) {
        this.traderAuthService = traderAuthService;
        this.jwtCookieProvider = jwtCookieProvider;
    }

    @PostMapping("/challenge")
    public ResponseEntity<ChallengeResponse> challenge(@RequestBody ChallengeRequest request) {
        ChallengeResponse response = traderAuthService.createChallenge(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody TraderLoginRequest request) {
        AuthResult result = traderAuthService.login(request);
        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.TRADER);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.TRADER);
        AuthResult result = traderAuthService.refresh(refreshToken);
        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.TRADER);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.TRADER);
        traderAuthService.logout(refreshToken);
        ResponseCookie cleared = jwtCookieProvider.clearRefreshCookie(RefreshTokenCookie.TRADER);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleared.toString())
                .build();
    }
}