package com.pichincha.money.repository;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.money.entity.ExchangeRateLog;

import reactor.core.publisher.Flux;

@Repository
public interface ExchangeRateLogRepository extends R2dbcRepository<ExchangeRateLog, Long> {

    // Buscar registros por moneda base
    Flux<ExchangeRateLog> findByBase(String base);

    // Buscar registros por moneda de destino
    Flux<ExchangeRateLog> findByTarget(String target);

    // Buscar registros por fecha de consulta (ejemplo: obtener logs del último día)
    Flux<ExchangeRateLog> findByQueryTimestampAfter(LocalDateTime timestamp);
}
