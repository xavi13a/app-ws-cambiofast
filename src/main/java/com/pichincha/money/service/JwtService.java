package com.pichincha.money.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {

    private final long expirationTime;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public JwtService(@Value("${app.jwt.expiration}") long expiration) {
        this.expirationTime = expiration;
    }
    
    public Mono<String> generateSessionToken(String email) {
        return Mono.fromSupplier(() -> {
            return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
        });
    }

    public String validateSessionToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Clave segura para JWT: " + base64Key);
    }
}