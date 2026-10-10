package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Book;
import com.dat.book_hub.domain.entity.BookTag;
import com.dat.book_hub.domain.entity.Tag;
import com.dat.book_hub.domain.repository.BookTagRepository;
import com.dat.book_hub.infrastructure.persistence.jpa.BookTagJpaRepository;

@Repository 
public class BookTagRepositoryImpl implements BookTagRepository {
    private final BookTagJpaRepository bookTagJpaRepository;

    public BookTagRepositoryImpl(BookTagJpaRepository bookTagJpaRepository) {
        this.bookTagJpaRepository = bookTagJpaRepository;
    }

    @Override
    public Optional<BookTag> findByBookIdAndTagId(Long bookId, Long tagId) {
        return bookTagJpaRepository.findByBook_BookIdAndTag_TagId(bookId, tagId);
    }

    @Override
    public boolean addTagToBook(Long bookId, Long tagId) {
        if (findByBookIdAndTagId(bookId, tagId).isPresent()) {
            return false; // Tag already associated with the book
        }
        int result = bookTagJpaRepository.insertBookTag(bookId, tagId);
        if(result == 0) {
            return false; // Failed to save the association
        }
        return true;
    }

}
