package com.dat.book_hub.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dat.book_hub.domain.entity.Tag;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);
}
