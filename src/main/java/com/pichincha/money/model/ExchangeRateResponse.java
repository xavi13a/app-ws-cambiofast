package com.pichincha.money.model;

import java.util.Map;

import lombok.Data;

@Data
public class ExchangeRateResponse {
	private boolean success;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
