package com.pichincha.money.entity;

import lombok.Data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("magic_link_token")
public class MagicLinkToken {

    @Id
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expiration;
    private boolean used = false;

    public MagicLinkToken() {}

    public MagicLinkToken(String token, String email, LocalDateTime expiration) {
        this.token = token;
        this.email = email;
        this.expiration = expiration;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration);
    }
}
