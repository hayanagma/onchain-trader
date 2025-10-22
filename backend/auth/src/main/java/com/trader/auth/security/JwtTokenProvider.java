package com.trader.auth.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtTokenProvider(
            PrivateKey privateKey,
            PublicKey publicKey,
            @Value("${JWT_ACCESS_EXPIRATION_MS}") long accessExpirationMs,
            @Value("${JWT_REFRESH_EXPIRATION_MS}") long refreshExpirationMs) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.accessTokenValidity = accessExpirationMs;
        this.refreshTokenValidity = refreshExpirationMs;
    }

    // --- Admin (String subject) ---
    public String createAccessToken(String subject, List<String> roles, int tokenVersion) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles)
                .claim("tv", tokenVersion)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String createRefreshToken(String subject, int tokenVersion) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("tv", tokenVersion)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    // --- Player (Long subject) ---
    public String createAccessToken(Long playerId, List<String> roles, int tokenVersion) {
        return createAccessToken(String.valueOf(playerId), roles, tokenVersion);
    }

    public String createRefreshToken(Long playerId, int tokenVersion) {
        return createRefreshToken(String.valueOf(playerId), tokenVersion);
    }

    // --- Parse ---
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey) // verify with public key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
