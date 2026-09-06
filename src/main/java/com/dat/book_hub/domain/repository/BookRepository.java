package com.dat.book_hub.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Book;

@Repository 
public interface BookRepository {
    Optional<Book> getBookByUserName(String username);
    
}
