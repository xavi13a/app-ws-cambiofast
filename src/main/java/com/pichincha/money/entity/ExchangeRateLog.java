package com.pichincha.money.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table("exchange_rate_log")
public class ExchangeRateLog {

    @Id
    private Long id;
    private String base;
    private Double exchangeRate;
    private String target;
    private LocalDateTime queryTimestamp;

    public ExchangeRateLog() {}

    public ExchangeRateLog(String base, Double exchangeRate, String target) {
        this.base = base;
        this.exchangeRate = exchangeRate;
        this.target = target;
        this.queryTimestamp = LocalDateTime.now();
    }
}
