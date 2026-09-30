package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.book_hub.domain.entity.Book;

import org.springframework.data.repository.query.Param;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    @Query(
            value = """
        SELECT b.*
        FROM Books b
        JOIN Users u ON b.user_id = u.user_id
        WHERE u.username = :username
    """,
            nativeQuery = true
    )
    List<Book> findByUsername(@Param("username") String username);
}
