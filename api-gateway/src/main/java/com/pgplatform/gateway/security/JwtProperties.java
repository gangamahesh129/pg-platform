package com.pgplatform.gateway.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * HS256 secret shared with the auth-service.
     * Prefer setting via env var and keep it at least 32 bytes.
     */
    private String secret;

    /**
     * Paths that do not require authentication (e.g. login/register).
     * Supports Spring Cloud Gateway path pattern matching (via PathPatternParser).
     */
    private List<String> publicPaths = new ArrayList<>();

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths == null ? new ArrayList<>() : publicPaths;
    }
}

