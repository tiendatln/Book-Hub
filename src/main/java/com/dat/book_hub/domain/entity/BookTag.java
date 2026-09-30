package com.dat.book_hub.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookTagId.class)
@Table(name="BookTags")
public class BookTag {
    @Id
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;
}
