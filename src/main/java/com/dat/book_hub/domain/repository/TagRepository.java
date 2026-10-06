package com.dat.book_hub.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Tag;
@Repository 
public interface TagRepository {
    Optional<Tag> findByTagName(String tagName);
    Optional<Tag> findById(Long tagId);
    Tag createTag(Tag tag);
    Tag updateTag(Tag tag);
    boolean deleteTag(Tag tag);
}
