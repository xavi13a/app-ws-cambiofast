package com.pichincha.money.controller;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("¡Hola desde WebFlux!");
    }
    
    public static void main(String[] args) {
        Mono.just("Hola WebFlux")
            .delayElement(Duration.ofSeconds(2)) // Retrasa 2 segundos
            .subscribe(System.out::println);

        System.out.println("Here");
        try { Thread.sleep(3000); } catch (InterruptedException e) { }
    }
}

