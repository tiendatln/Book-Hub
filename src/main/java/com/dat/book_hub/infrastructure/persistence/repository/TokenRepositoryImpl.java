package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.RefreshToken;
import com.dat.book_hub.domain.repository.TokenRepository;
import com.dat.book_hub.infrastructure.persistence.jpa.TokenJpaReponsitory;

@Repository 
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaReponsitory tokenJpaReponsitory;

    public TokenRepositoryImpl(TokenJpaReponsitory jpaReponsitory){
        this.tokenJpaReponsitory = jpaReponsitory;
    }

    @Override
    public Optional<RefreshToken> createRefreshToken(RefreshToken token) {
        this.tokenJpaReponsitory.save(token);
        Optional<RefreshToken> created = this.tokenJpaReponsitory.findTokenByToken(token.getToken());

        return created;
    }

    @Override
    public Optional<RefreshToken> updateRefreshToken(RefreshToken token) {
        this.tokenJpaReponsitory.save(token);
        return this.tokenJpaReponsitory.findTokenByToken(token.getToken());
    }

    @Override
    public boolean deleteRefreshToken(String token) {
        return this.tokenJpaReponsitory.deleteByToken(token) > 0;
    }

}
