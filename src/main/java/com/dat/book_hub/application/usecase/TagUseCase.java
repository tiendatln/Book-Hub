package com.dat.book_hub.application.usecase;

import com.dat.book_hub.application.dto.request.Tag.TagRequest;
import com.dat.book_hub.application.dto.response.Tag.TagResponse;

public interface TagUseCase {
    TagResponse createTag(TagRequest tagRequest);
    TagResponse updateTag(Long tagId, TagRequest tagRequest);
    boolean deleteTag(Long tagId);
    TagResponse getTagById(Long tagId);
    TagResponse getTagByName(String tagName);
}
