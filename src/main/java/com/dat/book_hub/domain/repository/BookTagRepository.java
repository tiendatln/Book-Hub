package com.dat.book_hub.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.BookTag;

@Repository 
public interface BookTagRepository {
    Optional<BookTag> findByBookIdAndTagId(Long bookId, Long tagId);
    boolean addTagToBook(Long bookId, Long tagId);
}
