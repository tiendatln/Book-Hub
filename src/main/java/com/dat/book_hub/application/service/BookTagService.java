package com.dat.book_hub.application.service;

import org.springframework.stereotype.Service;

import com.dat.book_hub.application.mapping.BookMapper;
import com.dat.book_hub.application.mapping.TagMapper;
import com.dat.book_hub.application.usecase.BookTagUseCase;
import com.dat.book_hub.domain.entity.Book;
import com.dat.book_hub.domain.entity.Tag;
import com.dat.book_hub.domain.repository.BookTagRepository;

@Service 
public class BookTagService implements BookTagUseCase {
    private final BookTagRepository bookTagRepository;
    private final TagService tagService;
    private final TagMapper tagMapper;

    
    public BookTagService(BookTagRepository bookTagRepository, TagService tagService, TagMapper tagMapper) {
        this.bookTagRepository = bookTagRepository;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @Override
    public boolean addTagToBook(Long bookId, Long tagId) {
        // TODO Auto-generated method stub

        return this.bookTagRepository.addTagToBook(bookId, tagId);
    }

}
