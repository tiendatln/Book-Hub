package com.dat.book_hub.application.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.request.Book.CreateBookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;
import com.dat.book_hub.application.mapping.BookMapper;
import com.dat.book_hub.application.usecase.BookUseCase;
import com.dat.book_hub.domain.entity.Book;
import com.dat.book_hub.domain.repository.BookRepository;
import com.dat.book_hub.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService implements BookUseCase{

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;
    private final BookTagService bookTagService;
    private final SupabaseStorageService supabaseStorageService;
    
    

    public BookService(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper, BookTagService bookTagService, SupabaseStorageService supabaseStorageService){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookMapper = bookMapper;
        this.bookTagService = bookTagService;
        this.supabaseStorageService = supabaseStorageService;
    }

    @Override
    public List<BookResponse> getBookByUsername(String username) {
        // TODO Auto-generated method stub
        return this.bookMapper.TListBookResponse(this.bookRepository.getBookByUserName(username));
    }

    @Transactional 
    @Override
    public BookResponse createBook(CreateBookRequest bookRequest, String username) {
        Book newBook = this.bookMapper.tBookCreate(bookRequest);
        try {
            String thumbnailUrl = this.supabaseStorageService.uploadImage(bookRequest.thumbnail(), username, bookRequest.title());
            newBook.setThumbnail(thumbnailUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload thumbnail", e);
        }
        newBook.setUser(this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        newBook = this.bookRepository.createBook((newBook));
        if (newBook == null) {
            throw new RuntimeException("Failed to create book");
        }
       
        if (bookRequest.tagIds().isEmpty() == false) {
            for (Long tagId : bookRequest.tagIds()) {
                this.bookTagService.addTagToBook(newBook.getBookId(), tagId);
            }
        }


        return this.bookMapper.tBookResponse(newBook);
    }

    @Override
    public List<BookResponse> getBooksPage(String search, String author, String tag, int start, int current) {
        List<Book> books = this.bookRepository.getBooksPage(search, author, tag, start, current);
        return this.bookMapper.TListBookResponse(books);
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        // TODO Auto-generated method stub
        return this.bookMapper.tBookResponse(this.bookRepository.getBookById(bookId));
    }

}
