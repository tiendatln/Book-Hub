package com.dat.book_hub.application.dto.response.Book;

import java.time.LocalDateTime;

public record BookResponse(
	Long bookId,
	String title,
	String author,
	String isbn,
	String description,
	boolean isPublic,
	String thumbnail,
	String url,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	String username
) {

}
