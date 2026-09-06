package com.dat.book_hub.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Books", indexes = {
        @jakarta.persistence.Index(name = "idx_title", columnList = "title"),
        @jakarta.persistence.Index(name = "idx_author", columnList = "author"),
        @jakarta.persistence.Index(name = "idx_isbn", columnList = "isbn")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(length = 255, nullable = false)
    private String title;
    @Column(nullable = false, length = 255)
    private String author;
    @Column(nullable = false, length = 100)
    private String isbn;
    private String description;
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    @Column(length = 255)
    private String thumbnail;
    private String url;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="book", fetch=FetchType.LAZY)
    private Set<BookTag> bookTags;
    @OneToMany(mappedBy="book", fetch=FetchType.LAZY)
    private Set<BookContent> bookContents;
    @OneToMany(mappedBy="book", fetch=FetchType.LAZY)
    private Set<Rating> ratings;
}
