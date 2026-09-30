package com.dat.book_hub.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;
import com.dat.book_hub.application.dto.response.DataResponse;
import com.dat.book_hub.application.dto.response.ListDataResponse;
import com.dat.book_hub.application.usecase.BookUseCase;


@RestController
@RequestMapping("/book")
public class BookController {

    private final BookUseCase bookService;

    public BookController(BookUseCase bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/get-book")
    public ResponseEntity<ListDataResponse<BookResponse>> getBookByUsername(@RequestParam("username") String username) {
        List<BookResponse> bookList = this.bookService.getBookByUsername(username);
        if (bookList.isEmpty()) {
            return ResponseEntity.ok(new ListDataResponse<>("User does not have any books.", bookList));
        }
        return ResponseEntity.ok(new ListDataResponse<>("Get list success!", bookList));
    }

    @PostMapping("/create-book")
    public ResponseEntity<DataResponse<BookResponse>> createBook(
            @RequestBody BookRequest bookRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        BookResponse bookResponse = this.bookService.createBook(bookRequest, userDetails.getUsername());

        if(bookResponse == null){
            return ResponseEntity.status(500).body(new DataResponse<>("Can not create book!", bookResponse));
        }
        
        return ResponseEntity.status(200).body(new DataResponse<>("Book created success!", bookResponse));
    }
    

}
