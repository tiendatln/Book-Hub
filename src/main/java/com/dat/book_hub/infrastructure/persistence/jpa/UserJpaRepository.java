package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dat.book_hub.domain.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("""
    SELECT DISTINCT u
    FROM User u
    LEFT JOIN FETCH u.books
    WHERE u.username = :username
""")
    Optional<User> findUserIncludeBookByUsername(@Param("username") String username);

    @Query(
            value = """
        SELECT *
        FROM users
        WHERE username = :username
        """,
            nativeQuery = true
    )
    User findUserByUsername(@Param("username") String username);

}
