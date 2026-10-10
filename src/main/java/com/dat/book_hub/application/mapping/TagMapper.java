package com.dat.book_hub.application.mapping;

import java.util.List;

import org.mapstruct.Mapper;

import com.dat.book_hub.application.dto.request.Tag.CreateTagRequest;
import com.dat.book_hub.application.dto.request.Tag.TagRequest;
import com.dat.book_hub.application.dto.response.Tag.TagResponse;
import com.dat.book_hub.domain.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse toTagResponse(Tag tag);
    Tag toTagEntity(TagRequest tagRequest);
    Tag toTagEntity(TagResponse tagResponse);
    List<TagResponse> toTagResponseList(List<Tag> tags);
    Tag toTagEntityCreate(CreateTagRequest tagRequest);
}
