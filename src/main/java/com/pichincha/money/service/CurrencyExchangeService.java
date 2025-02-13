package com.pichincha.money.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pichincha.money.model.ExchangeRateResponse;

import reactor.core.publisher.Mono;

@Service
public class CurrencyExchangeService {
    private final WebClient webClient;

    @Value("${exchangeratesapi.key}")
    private String apiKey;

    public CurrencyExchangeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Double> getExchangeRate(String base, String target) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("access_key", apiKey)
                    .queryParam("base", base) 
                    .build())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .map(response -> response.getRates().get(target));
    }

    public Mono<Double> convertCurrency(double amount, String base, String target) {
        return getExchangeRate(base, target)
                .map(rate -> amount * rate);
    }
}
