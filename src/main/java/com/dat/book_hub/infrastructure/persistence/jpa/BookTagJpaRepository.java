package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dat.book_hub.domain.entity.BookTag;
import com.dat.book_hub.domain.entity.BookTagId;

import io.lettuce.core.dynamic.annotation.Param;

public interface BookTagJpaRepository extends JpaRepository<BookTag, BookTagId> {
    Optional<BookTag> findByBook_BookIdAndTag_TagId(Long bookId, Long tagId);

    @Modifying
    @Query(value = """
            INSERT INTO book_tags (book_id, tag_id)
            VALUES (:bookId, :tagId)
            """, nativeQuery = true)
    int insertBookTag(
            @Param("bookId") Long bookId,
            @Param("tagId") Long tagId);

}
