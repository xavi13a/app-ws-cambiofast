package com.pichincha.money.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("currency")
public class Currency {

    @Id
    private Long id;
    private String code;
    private String name;

    public Currency() {}

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
