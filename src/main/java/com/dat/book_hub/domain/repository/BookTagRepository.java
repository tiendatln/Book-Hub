package com.dat.book_hub.domain.repository;

import java.util.Optional;

import com.dat.book_hub.domain.entity.BookTag;

public interface BookTagRepository {
    Optional<BookTag> findByBookIdAndTagId(Long bookId, Long tagId);
}
