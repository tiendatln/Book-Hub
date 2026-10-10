package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dat.book_hub.domain.entity.Book;

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
    List<Book> findBookByUser_Username(@Param("username") String username);

    @Query(
            value = """
        SELECT b.*
        FROM Books b
        JOIN Users u ON b.user_id = u.user_id
        WHERE (:search IS NULL OR b.title LIKE %:search%)
          AND (:author IS NULL OR b.author LIKE %:author%)
          AND (:tag IS NULL OR EXISTS (
              SELECT 1
              FROM Book_Tags bt
              JOIN Tags t ON bt.tag_id = t.tag_id
              WHERE bt.book_id = b.book_id AND t.tagName LIKE %:tag%
          ))
        ORDER BY b.book_id DESC
        LIMIT :current OFFSET :start
    """,
            nativeQuery = true
    )
    List<Book> findBooksPage(@Param("search") String search, 
    @Param("author") String author, 
    @Param("tag") String tag, 
    @Param("start") int start, 
    @Param("current") int current);
}
