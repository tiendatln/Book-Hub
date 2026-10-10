package com.dat.book_hub.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dat.book_hub.domain.entity.Tag;
import com.dat.book_hub.domain.repository.TagRepository;
import com.dat.book_hub.infrastructure.persistence.jpa.TagJpaRepository;

@Repository 
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;

    public TagRepositoryImpl(TagJpaRepository tagJpaRepository) {
        this.tagJpaRepository = tagJpaRepository;
    }

    @Override
    public Optional<Tag> getByTagName(String tagName) {
        return tagJpaRepository.findByTagName(tagName);
    }

    @Override
    public Optional<Tag> getTagById(Long tagId) {
        return tagJpaRepository.findById(tagId);
    }

    @Override
    public Tag createTag(Tag tag) {
        // TODO Auto-generated method stub
        return tagJpaRepository.save(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        // TODO Auto-generated method stub
        return tagJpaRepository.save(tag);
    }

    @Override
    public boolean deleteTag(Tag tag) {
        // TODO Auto-generated method stub
        try {
            tagJpaRepository.delete(tag);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Tag> getAllTags() {
        // TODO Auto-generated method stub
        return tagJpaRepository.findAll();
    }

}
