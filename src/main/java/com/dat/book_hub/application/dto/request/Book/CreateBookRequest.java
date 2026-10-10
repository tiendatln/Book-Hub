package com.dat.book_hub.application.dto.request.Book;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CreateBookRequest(
    String title,
	String author,
	String isbn,
	String description,
	boolean isPublic,
	MultipartFile thumbnail,
	String url,
    List<Long> tagIds
) {

}
