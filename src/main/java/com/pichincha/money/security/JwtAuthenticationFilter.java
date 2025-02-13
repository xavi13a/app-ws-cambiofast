package com.pichincha.money.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.pichincha.money.service.JwtService;

import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        
    	ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
    	
    	String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap("No JWT token provided".getBytes())));
        }

        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.validateSessionToken(token);

        if (email == null) {
        	response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap("Invalid or expired token".getBytes())));
        }

        // Si el token es válido, continuar con la solicitud
        return chain.filter(exchange);
    }
}