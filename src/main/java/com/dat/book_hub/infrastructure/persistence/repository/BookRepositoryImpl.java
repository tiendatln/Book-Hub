package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Book;
import com.dat.book_hub.domain.repository.BookRepository;
import com.dat.book_hub.infrastructure.persistence.jpa.BookJpaRepository;
@Repository 
public class BookRepositoryImpl implements BookRepository{

    private final BookJpaRepository bookRepository;

    public BookRepositoryImpl(BookJpaRepository bjr){
        this.bookRepository = bjr;
    }

    @Override
    public List<Book> getBookByUserName(String username) {
        // TODO Auto-generated method stub
        return this.bookRepository.findByUsername(username);
    }

    @Override
    public Book createBook(Book book) {
        // TODO Auto-generated method stub
        Book newBook = this.bookRepository.save(book);
        return newBook;
    }

    @Override
    public Book updateBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public List<Book> getBooksPage(String search, String author, String tag, int start, int current) {
        // TODO Auto-generated method stub
        return this.bookRepository.findBooksPage(search, author, tag, start, current);
    }

}
