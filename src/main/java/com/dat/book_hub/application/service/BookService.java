package com.dat.book_hub.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;
import com.dat.book_hub.application.mapping.BookMapper;
import com.dat.book_hub.application.usecase.BookUseCase;
import com.dat.book_hub.domain.entity.Book;
import com.dat.book_hub.domain.repository.BookRepository;
import com.dat.book_hub.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class BookService implements BookUseCase{

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookResponse> getBookByUsername(String username) {
        // TODO Auto-generated method stub
        return this.bookMapper.TListBookResponse(this.bookRepository.getBookByUserName(username));
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest, String username) {
        Book newBook = this.bookMapper.tBook(bookRequest);
        newBook.setUser(this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));

        return this.bookMapper.tBookResponse(this.bookRepository.createBook((newBook)));
    }

}
