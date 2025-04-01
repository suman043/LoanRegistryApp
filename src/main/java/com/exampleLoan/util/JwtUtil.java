package com.exampleLoan.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final long EXPIRATION_TIME = 1000 * 60 * 10; // 10 minutes

    public String generateToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 minutes expiry
                .signWith(key) // Use secure key
                .compact();

        // Store token in Redis with expiration time
        redisTemplate.opsForValue().set(email, token, EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        return token;
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
        //return email.equals(extractEmail(token)) && !isTokenExpired(token);
        String storedToken = redisTemplate.opsForValue().get(email);
        return token.equals(storedToken) && !isTokenExpired(token);
    }

    public void invalidateToken(String email) {
        redisTemplate.delete(email);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Use secure key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}