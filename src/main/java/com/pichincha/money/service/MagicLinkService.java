package com.pichincha.money.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pichincha.money.entity.MagicLinkToken;
import com.pichincha.money.repository.MagicLinkTokenRepository;

import javax.crypto.SecretKey;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MagicLinkService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final SendGridEmailService emailService;
    private final MagicLinkTokenRepository tokenRepository;
    
    @Value("${app.magic-link.expiration}")
    private long expiration;

    @Value("${app.magic-link.base-url}")
    private String baseUrl;

    public MagicLinkService(SendGridEmailService emailService, MagicLinkTokenRepository tokenRepository) {
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

//    public String generateMagicLink(String email) {
//        String token = Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SECRET_KEY)
//                .compact();
//
//        MagicLinkToken magicLinkToken = new MagicLinkToken(token, email, LocalDateTime.now().plusMinutes(expiration / 60000));
//        tokenRepository.save(magicLinkToken);
//        
//        return baseUrl + "/login?token=" + token;
//    }
    
    public Mono<String> generateMagicLink(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY)
                .compact();

        MagicLinkToken magicLinkToken = new MagicLinkToken(token, email, LocalDateTime.now().plusMinutes(expiration / 60000));

        return tokenRepository.save(magicLinkToken)
                .thenReturn(baseUrl + "/login?token=" + token);
    }

//    public void sendMagicLink(String email) throws MessagingException {
//        String magicLink = generateMagicLink(email);
//        emailService.sendEmail(email, magicLink);
//    }
    
    public Mono<Void> sendMagicLink(String email) {
        return generateMagicLink(email)
                .flatMap(magicLink -> {
                    emailService.sendEmail(email, magicLink);
					return Mono.empty();
                });
    }

    public Mono<String> validateToken(String token) {
        return tokenRepository.findByToken(token)
            .filter(storedToken -> !storedToken.isUsed() && !storedToken.isExpired()) // Filtra tokens válidos
            .flatMap(storedToken -> {
                try {
                    String email = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                    // Marcar token como usado
                    storedToken.setUsed(true);
                    return tokenRepository.save(storedToken) // Guardar el token actualizado
                        .thenReturn(email); // Retornar el email después de guardar
                } catch (Exception e) {
                    return Mono.empty(); // Token inválido
                }
            })
            .switchIfEmpty(Mono.empty()); // Retorna vacío si el token no es válido
    }
}
