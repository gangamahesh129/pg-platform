package com.pgplatform.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthenticationGlobalFilter implements GlobalFilter, Ordered {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final JwtProperties properties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationGlobalFilter(JwtUtil jwtUtil, JwtProperties properties) {
        this.jwtUtil = Objects.requireNonNull(jwtUtil, "jwtUtil");
        this.properties = Objects.requireNonNull(properties, "properties");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isPublicPath(request.getPath(), properties.getPublicPaths())) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isEmpty()) {
            return unauthorized(exchange);
        }

        try {
            Jws<Claims> jws = jwtUtil.parseAndValidate(token);

            // Optional: forward useful identity headers to downstream services
            String subject = jws.getPayload().getSubject();
            String role = jws.getPayload().get("role", String.class);

            ServerHttpRequest mutated = request.mutate()
                    .headers(headers -> {
                        if (subject != null && !subject.isBlank()) {
                            headers.set("X-Auth-Subject", subject);
                        }
                        if (role != null && !role.isBlank()) {
                            headers.set("X-Auth-Role", role);
                        }
                    })
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());
        } catch (JwtException ex) {
            return unauthorized(exchange);
        }
    }

    private boolean isPublicPath(PathContainer path, List<String> publicPaths) {
        if (publicPaths == null || publicPaths.isEmpty()) {
            return false;
        }
        String value = path.value();
        for (String pattern : publicPaths) {
            if (pattern != null && !pattern.isBlank() && pathMatcher.match(pattern.trim(), value)) {
                return true;
            }
        }
        return false;
    }

    private static Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    /**
     * Run early, before routing happens.
     */
    @Override
    public int getOrder() {
        return -100;
    }
}

