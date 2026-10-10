package com.dat.book_hub.application.usecase;

import java.util.List;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.request.Book.CreateBookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;

public interface BookUseCase {
    List<BookResponse> getBookByUsername(String username);
    BookResponse getBookById(Long bookId);
    BookResponse createBook(CreateBookRequest bookRequest, String username);
    List<BookResponse> getBooksPage(String search, String author, String tag, int start, int current);
}
