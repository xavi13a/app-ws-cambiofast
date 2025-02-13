package com.pichincha.money.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pichincha.money.service.MagicLinkService;

import jakarta.mail.MessagingException;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final MagicLinkService magicLinkService;

    public AuthController(MagicLinkService magicLinkService) {
        this.magicLinkService = magicLinkService;
    }

    @PostMapping("/request-magic-link")
    public Mono<ResponseEntity<String>> requestMagicLink(@RequestParam String email) {
        return magicLinkService.sendMagicLink(email)
                .then(Mono.just(ResponseEntity.ok("Magic link enviado a tu correo.")))
                .onErrorResume(MessagingException.class, e -> 
                    Mono.just(ResponseEntity.status(500).body("Error enviando el correo."))
                );
    }
    
//    @PostMapping("/request-magic-link")
//    public ResponseEntity<String> requestMagicLink(@RequestParam String email) {
//        try {
//            magicLinkService.sendMagicLink(email);
//            return ResponseEntity.ok("Magic link enviado a tu correo.");
//        } catch (MessagingException e) {
//            return ResponseEntity.status(500).body("Error enviando el correo.");
//        }
//    }
}
