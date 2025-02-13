package com.pichincha.money.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY;
    private final long expirationTime;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public JwtService(@Value("${app.jwt.secret}") String secret, 
                      @Value("${app.jwt.expiration}") long expiration) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTime = expiration;
    }

    // Generar JWT de sesión
//    public String generateSessionToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(secretKey)
//                .compact();
//    }
    
    public Mono<String> generateSessionToken(String email) {
        return Mono.fromSupplier(() -> {
            return Jwts.builder()
                .setSubject(email)
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
        });
    }

    // Validar JWT de sesión
    public String validateSessionToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null; // Token inválido
        }
    }
    
    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Clave segura para JWT: " + base64Key);
    }
}