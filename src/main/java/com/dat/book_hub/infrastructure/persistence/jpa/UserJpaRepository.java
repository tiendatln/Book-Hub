package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.dat.book_hub.domain.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(@Param("username") String username);

    
    User findLoginByUsername(@Param("username") String username);

}