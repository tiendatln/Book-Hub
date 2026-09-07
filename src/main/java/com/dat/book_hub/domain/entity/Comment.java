package com.dat.book_hub.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter 
@Getter 
@AllArgsConstructor 
@Table(name="Comments")
public class Comment {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;
    @Column(nullable=false)
    private String commentContent;
    @Column(nullable=false)
    @CreationTimestamp 
    private LocalDateTime createdAt;
    @ManyToOne 
    @JoinColumn(name="book_id")
    private Book book;
}
