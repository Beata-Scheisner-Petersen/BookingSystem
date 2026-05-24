package org.example.bookingsystem.security.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.security.*;
import java.util.*;

/*
* @Service makes the class a Spring bean.
* It is responsible for creating, signing, and reading JWT tokens.
*/
@Service
public class JwtService {
    private final Key key;

    /*
    * You are reading jwt.secret from application.properties.
    * You convert it into an HMAC SHA key.
    * Keys.hmacShaKeyFor(...) requires at least 32 characters for HS256/HS512.
     */
    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /*
    * Creating JWT.
    * .setSubject(customerId.toString()) -> This is the standard field for 'who is the user?'.
    * .claim("email", email) -> You add email as extra data in the token.
    * .setIssuedAt(new Date()) -> sets when the token was created.
    * .setExpiration(...) -> sets when token expiration.
        * Token is valid for 1 hour!
    * .signWith(key, SignatureAlgorithm.HS512) -> You sign the token with your secret key.
    * .compact(); -> A compact JWT string that the client receives at login.
     */
    public String generateToken(Long customerId, String email) {
        return Jwts.builder()
                .setSubject(customerId.toString())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /*
     * You build a parser with your signing key.
     * You validate the token's signature, expiration, and format.
     * You retrieve the subject, which is customerId.
     * All exceptions are caught in JwtAuthFilter.
     */
    public Long extractCustomerId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    /*
     * You build a parser with your signing key.
     * You validate the token's signature, expiration, and format.
     * You retrieve the claim, which is email.
     * All exceptions are caught in JwtAuthFilter.
     */
    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }


    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String email = extractEmail(token);
        return email != null && email.equals(userDetails.getUsername());
    }
}
