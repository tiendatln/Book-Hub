package com.dat.book_hub.domain.repository;

import java.util.Optional;

import com.dat.book_hub.domain.entity.RefreshToken;

public interface TokenRepository {

    Optional<RefreshToken> createRefreshToken(RefreshToken token);

    Optional<RefreshToken> updateRefreshToken(RefreshToken token, Long refreshTokenId);

    boolean deleteRefreshToken(String token);

    Optional<RefreshToken> getRefreshTokenByToken(String Token);
}
