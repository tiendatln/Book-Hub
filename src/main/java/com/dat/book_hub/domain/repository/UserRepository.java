package com.dat.book_hub.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.User;

@Repository
public interface UserRepository{
    Optional<User> findByUsername(String username);
    boolean createUser(User user);
    boolean updateUser(User user);
    User findUserLogin(String username);

}
