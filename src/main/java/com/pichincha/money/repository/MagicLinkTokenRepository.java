package com.pichincha.money.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import com.pichincha.money.entity.MagicLinkToken;
import reactor.core.publisher.Mono;

@Repository
public interface MagicLinkTokenRepository extends R2dbcRepository<MagicLinkToken, Long> {
	Mono<MagicLinkToken> findByToken(String token);
}

