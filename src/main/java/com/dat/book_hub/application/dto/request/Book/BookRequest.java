package com.dat.book_hub.application.dto.request.Book;

public record BookRequest(
	String title,
	String author,
	String isbn,
	String description,
	boolean isPublic,
	String thumbnail,
	String url
) {

}
