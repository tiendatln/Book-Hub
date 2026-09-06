package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.User;
import com.dat.book_hub.domain.repository.UserRepository;
import com.dat.book_hub.infrastructure.persistence.jpa.UserJpaRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userRepository;
    
    public UserRepositoryImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Async
    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Async
    @Override
    public boolean createUser(User user) {
        this.userRepository.save(user);
        return true;
    }

    @Async
    @Override
    public boolean updateUser(User user) {
        this.userRepository.save(user);
        return true;
    }

    @Async
    @Override
    public User findUserLogin(String username) {
        return this.userRepository.findLoginByUsername(username);
    }

}
