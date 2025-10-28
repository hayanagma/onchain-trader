package com.trader.gateway.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.trader.gateway.security.CustomRoleConverter;
import com.trader.gateway.security.TokenValidator;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final TokenValidator tokenValidator;

    public SecurityConfig(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**", "/actuator/health").permitAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/trader/**").hasRole("TRADER")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
            @Override
            public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
                String subject = jwt.getSubject();
                List<String> roles = jwt.getClaimAsStringList("roles");

                Object tvClaim = jwt.getClaim("tv");
                int tokenVersion = (tvClaim instanceof Number n) ? n.intValue() : 0;

                JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();
                delegate.setJwtGrantedAuthoritiesConverter(new CustomRoleConverter());

                return Mono.fromRunnable(() -> tokenValidator.validate(roles.get(0), subject, tokenVersion))
                        .then(Mono.fromCallable(() -> delegate.convert(jwt)));
            }
        };
    }
}
