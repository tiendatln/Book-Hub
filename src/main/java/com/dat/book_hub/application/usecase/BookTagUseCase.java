package com.dat.book_hub.application.usecase;


public interface BookTagUseCase {
    boolean addTagToBook(Long bookId, Long tagId);
}
