package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.Optional;

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

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.userRepository.findUserByUsername(username));
    }

    @Override
    public User createUser(User user) {
        this.userRepository.save(user);
        return this.userRepository.findUserByUsername(user.getUsername());
    }

    @Override
    public User updateUser(User user) {
        this.userRepository.save(user);
        return this.userRepository.findUserByUsername(user.getUsername());
    }

    @Override
    public User findUserLogin(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> getUserAndBookByUsername(String username) {
        // TODO Auto-generated method stub
        return this.userRepository.findUserIncludeBookByUsername(username);
    }

}
