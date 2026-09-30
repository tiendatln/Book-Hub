package com.dat.book_hub.application.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.dat.book_hub.domain.entity.Book;

public record UserResponseDto(
    String username,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Set<Book> books
) {

}
