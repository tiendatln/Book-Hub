package com.dat.book_hub.presentation.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.request.Book.CreateBookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;
import com.dat.book_hub.application.dto.response.DataResponse;
import com.dat.book_hub.application.dto.response.ListDataResponse;
import com.dat.book_hub.application.usecase.BookTagUseCase;
import com.dat.book_hub.application.usecase.BookUseCase;


@RestController
@RequestMapping("/book")
public class BookController {

    private final BookUseCase bookService;

    public BookController(BookUseCase bookService) {
        this.bookService = bookService;

    }

    @GetMapping("/get-book-user")
    public ResponseEntity<ListDataResponse<BookResponse>> getBookByUsername(@RequestParam("username") String username) {
        List<BookResponse> bookList = this.bookService.getBookByUsername(username);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok(new ListDataResponse<>("User does not have any books.", bookList));
        }
        return ResponseEntity.ok(new ListDataResponse<>("Get list success!", bookList));
    }

    @PostMapping(value = "/create-book",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<DataResponse<BookResponse>> createBook(
            @ModelAttribute CreateBookRequest bookRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (bookRequest == null) {
            return ResponseEntity.badRequest().body(new DataResponse<>("Book request is required.", null));
        }

        if (userDetails == null || userDetails.getUsername() == null || userDetails.getUsername().isBlank()) {
            return ResponseEntity.status(401).body(new DataResponse<>("Authentication required to create a book.", null));
        }

        BookResponse bookResponse = this.bookService.createBook(bookRequest, userDetails.getUsername());

        if (bookResponse == null) {
            return ResponseEntity.status(500).body(new DataResponse<>("Can not create book!", null));
        }

        return ResponseEntity.status(200).body(new DataResponse<>("Book created success!", bookResponse));
    }

    @GetMapping("/get-book-search-page")
    public ResponseEntity<ListDataResponse<BookResponse>> getBookPage(
        @RequestParam(required = false, defaultValue = "") String search,
        @RequestParam(required = false, defaultValue = "") String author,
        @RequestParam(required = false, defaultValue = "") String tag,
        @RequestParam(defaultValue = "1") int start) {
        List<BookResponse> bookList = this.bookService.getBooksPage(search, author, tag, start, 10);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok(new ListDataResponse<>("No books found for the specified page.", bookList));
        }
        return ResponseEntity.ok(new ListDataResponse<>("Get list success!", bookList));
    }
    

}
