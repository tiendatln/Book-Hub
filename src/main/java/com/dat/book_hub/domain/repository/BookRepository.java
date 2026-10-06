package com.dat.book_hub.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Book;

@Repository 
public interface BookRepository {
    List<Book> getBookByUserName(String username);
    Book createBook(Book book);
    Book updateBook(Book book);
    List<Book> getBooksPage(String search,String author, String tag, int start, int current);
}
