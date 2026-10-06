package com.dat.book_hub.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dat.book_hub.domain.entity.BookTag;

public interface BookTagJpaRepository extends JpaRepository<BookTag, Long> {

}
