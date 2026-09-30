package com.dat.book_hub.application.mapping;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dat.book_hub.application.dto.request.Book.BookRequest;
import com.dat.book_hub.application.dto.response.Book.BookResponse;
import com.dat.book_hub.domain.entity.Book;

@Mapper(componentModel="spring")
public interface BookMapper {
    List<BookResponse> TListBookResponse(List<Book> book);

    @Mapping(target = "username", source = "user.username")
    BookResponse tBookResponse(Book book);

    Book tBook(BookRequest bookRequest);
}
