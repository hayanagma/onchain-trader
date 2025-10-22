package com.trader.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.auth.dto.AuthResult;
import com.trader.auth.dto.LoginResponse;
import com.trader.auth.dto.player.ChallengeRequest;
import com.trader.auth.dto.player.ChallengeResponse;
import com.trader.auth.dto.player.PlayerLoginRequest;
import com.trader.auth.security.JwtCookieProvider;
import com.trader.auth.security.RefreshTokenCookie;
import com.trader.auth.service.PlayerAuthService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/auth/player")
public class PlayerAuthController {

    private final PlayerAuthService playerAuthService;
    private final JwtCookieProvider jwtCookieProvider;

    public PlayerAuthController(PlayerAuthService playerAuthService, JwtCookieProvider jwtCookieProvider) {
        this.playerAuthService = playerAuthService;
        this.jwtCookieProvider = jwtCookieProvider;
    }

    @PostMapping("/challenge")
    public ResponseEntity<ChallengeResponse> challenge(@RequestBody ChallengeRequest request) {
        ChallengeResponse response = playerAuthService.createChallenge(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody PlayerLoginRequest request) {
        AuthResult result = playerAuthService.login(request);
        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.PLAYER);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.PLAYER);
        AuthResult result = playerAuthService.refresh(refreshToken);
        ResponseCookie cookie = jwtCookieProvider.createRefreshCookie(result.getRefreshToken(),
                RefreshTokenCookie.PLAYER);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request, RefreshTokenCookie.PLAYER);
        playerAuthService.logout(refreshToken);
        ResponseCookie cleared = jwtCookieProvider.clearRefreshCookie(RefreshTokenCookie.PLAYER);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleared.toString())
                .build();
    }
}
