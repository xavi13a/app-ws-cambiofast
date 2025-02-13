package com.pichincha.money.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.money.service.CurrencyExchangeService;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/currency")
public class CurrencyExchangeController {
    private final CurrencyExchangeService exchangeService;

    public CurrencyExchangeController(CurrencyExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/exchange-rate")
    public Mono<ResponseEntity<Double>> getExchangeRate(
            @RequestParam String base,
            @RequestParam String target) {
        return exchangeService.getExchangeRate(base, target)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/convert")
    public Mono<ResponseEntity<Double>> convertCurrency(
            @RequestParam double amount,
            @RequestParam String base,
            @RequestParam String target) {
        return exchangeService.convertCurrency(amount, base, target)
                .map(ResponseEntity::ok);
    }
}
