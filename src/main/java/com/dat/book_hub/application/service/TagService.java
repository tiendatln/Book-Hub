package com.dat.book_hub.application.service;

import com.dat.book_hub.application.dto.request.Tag.TagRequest;
import com.dat.book_hub.application.dto.response.Tag.TagResponse;
import com.dat.book_hub.application.mapping.TagMapper;
import com.dat.book_hub.application.usecase.TagUseCase;
import com.dat.book_hub.domain.entity.Tag;
import com.dat.book_hub.domain.repository.TagRepository;

public class TagService implements TagUseCase {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        // TODO Auto-generated method stub
        Tag tag = this.tagMapper.toTagEntity(tagRequest);
        Tag savedTag = this.tagRepository.createTag(tag);
        return this.tagMapper.toTagResponse(savedTag);
    }

    @Override
    public TagResponse updateTag(Long tagId, TagRequest tagRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTag'");
    }

    @Override
    public boolean deleteTag(Long tagId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTag'");
    }

    @Override
    public TagResponse getTagById(Long tagId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTagById'");
    }

    @Override
    public TagResponse getTagByName(String tagName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTagByName'");
    }

}
