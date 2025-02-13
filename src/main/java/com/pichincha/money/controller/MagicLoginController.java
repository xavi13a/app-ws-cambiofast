package com.pichincha.money.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pichincha.money.service.JwtService;
import com.pichincha.money.service.MagicLinkService;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class MagicLoginController {

    private final MagicLinkService magicLinkService;
    private final JwtService jwtService;

    public MagicLoginController(MagicLinkService magicLinkService, JwtService jwtService) {
        this.magicLinkService = magicLinkService;
        this.jwtService = jwtService;
    }

    @GetMapping("/magic-login")
    public Mono<ResponseEntity<String>> magicLogin(@RequestParam String token) {
        return magicLinkService.validateToken(token)
            .flatMap(email -> jwtService.generateSessionToken(email) // Generar JWT después de validar token
                .map(sessionToken -> ResponseEntity.ok()
                    .body("{\"sessionToken\": \"" + sessionToken + "\"}"))
            )
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado."));
    }
}
