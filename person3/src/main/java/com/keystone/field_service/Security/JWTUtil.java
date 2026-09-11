package com.keystone.field_service.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private final Key key;

    // Token valid for 12 hours
    private final long validTokenTime = 12 * 60 * 60 * 1000L;

    public JWTUtil() {

        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                    "JWT_SECRET environment variable is not set"
            );
        }

        key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Generate JWT token
    public String generateToken(String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + validTokenTime
        );

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // Get email from JWT token
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}