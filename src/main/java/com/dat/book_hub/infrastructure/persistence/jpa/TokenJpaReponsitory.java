package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dat.book_hub.domain.entity.RefreshToken;

public interface TokenJpaReponsitory extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findTokenByToken(String token);

    int deleteByToken(String token);

    Optional<RefreshToken> findTokenByRefreshTokenId(Long tokenId);
}
