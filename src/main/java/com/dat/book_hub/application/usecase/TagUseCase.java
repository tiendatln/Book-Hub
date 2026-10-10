package com.dat.book_hub.application.usecase;

import java.util.List;

import com.dat.book_hub.application.dto.request.Tag.CreateTagRequest;
import com.dat.book_hub.application.dto.request.Tag.TagRequest;
import com.dat.book_hub.application.dto.response.Tag.TagResponse;

public interface TagUseCase {
    TagResponse createTag(CreateTagRequest tagRequest);
    TagResponse updateTag(Long tagId, TagRequest tagRequest);
    boolean deleteTag(Long tagId);
    TagResponse getTagById(Long tagId);
    TagResponse getTagByName(String tagName);
    List<TagResponse> getAllTags();
}
