package com.pichincha.money.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
//        	.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF si no es necesario
            .authorizeExchange(auth -> auth
                .pathMatchers("/auth/**").permitAll() // Permitir acceso público a Magic Links
                .pathMatchers("/currency/**").permitAll() // Permitir acceso público a la API de cambio de divisas
                .anyExchange().authenticated()
            )
            .build();
    }
    
    /**
     * Necesario ¿?
     * @return
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // Permitir todos los orígenes, cámbialo si es necesario
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
