package com.example.projectTLearn.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private static final long ACCESS_TOKEN_EXPIRATION_MS = 15L * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 60L * 24 * 60 * 60 * 1000;

    // Lấy Secret từ cấu hình
    private SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(String user_id, long expirationMs, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(user_id)
                .claim("tokenType", tokenType)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAccessToken(String user_id) {
        return generateToken(user_id, jwtExpiration > 0 ? jwtExpiration : ACCESS_TOKEN_EXPIRATION_MS, "ACCESS");
    }

    public String generateRefreshToken(String user_id) {
        return generateToken(user_id, REFRESH_TOKEN_EXPIRATION_MS, "REFRESH");
    }

    public String generateToken(String user_id) {
        return generateAccessToken(user_id);
    }

    // Giải mã chuỗi jwt lấy thông tin
    public String getUserFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Hàm xác thực token có hợp lệ / hết hạn ?

    public boolean validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token.trim())
                    .getPayload();

            return "ACCESS".equals(claims.get("tokenType", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token.trim())
                    .getPayload();

            return "REFRESH".equals(claims.get("tokenType", String.class));
        } catch (Exception e) {
            return false;
        }
    }
}
