package org.example.bookingsystem.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import java.security.*;
import java.util.*;

@Service
public class JwtService {
    private final Key key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generatedToken(Long CustomerId, String email) {
        return Jwts.builder()
                .setSubject(CustomerId.toString())
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // giltighetstid 1h
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();
    }

    public Long extractCustomerId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
