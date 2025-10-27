package com.trader.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtCookieProvider {

    @Value("${COOKIE_REFRESH_SECURE}")
    private boolean secure;

    @Value("${COOKIE_REFRESH_SAME_SITE}")
    private String sameSite;

    @Value("${COOKIE_REFRESH_MAX_AGE}")
    private long maxAge;

    public ResponseCookie createRefreshCookie(String token, RefreshTokenCookie type) {
        return ResponseCookie.from(type.getName(), token)
                .httpOnly(true)
                .secure(secure)
                .path("/")
                .sameSite(sameSite)
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie clearRefreshCookie(RefreshTokenCookie type) {
        return ResponseCookie.from(type.getName(), "")
                .httpOnly(true)
                .secure(secure)
                .path("/")
                .sameSite(sameSite)
                .maxAge(0)
                .build();
    }

    public String extractRefreshToken(HttpServletRequest request, RefreshTokenCookie type) {
        var cookie = WebUtils.getCookie(request, type.getName());
        return cookie != null ? cookie.getValue() : null;
    }
}