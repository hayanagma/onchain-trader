package com.trader.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;


@Component
public class IdentityHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .cast(JwtAuthenticationToken.class)
                .flatMap(auth -> {
                    Jwt jwt = auth.getToken();
                    String subject = jwt.getSubject();
                    List<String> roles = jwt.getClaimAsStringList("roles");

                    if (subject != null && roles != null && !roles.isEmpty()) {
                        String role = roles.get(0).replace("ROLE_", "");
                        ServerWebExchange mutated = exchange.mutate()
                                .request(r -> r.headers(headers -> {
                                    headers.set("X-User-Id", subject);
                                    headers.set("X-User-Role", role);
                                }))
                                .build();
                        return chain.filter(mutated);
                    }
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}


