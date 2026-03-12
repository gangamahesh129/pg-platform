package com.pgplatform.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class JwtUtil {
    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtUtil(JwtProperties properties) {
        this.properties = Objects.requireNonNull(properties, "properties");
        this.signingKey = buildSigningKey(properties.getSecret());
    }

    /**
     * Validates signature and standard claims (including exp) for a compact JWS.
     * Returns parsed claims if valid; throws JwtException (including ExpiredJwtException) if invalid.
     */
    public Jws<Claims> parseAndValidate(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
    }

    private static SecretKey buildSigningKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("jwt.secret must be configured");
        }

        /*
         * Supports either:
         * - raw string secret (common in dev)
         * - base64 encoded secret (common in prod)
         *
         * We try base64 first; if it doesn't decode to a usable length, fall back to raw bytes.
         */
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException e) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean isExpired(JwtException ex) {
        return ex instanceof ExpiredJwtException;
    }
}

