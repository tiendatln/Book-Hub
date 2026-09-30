package com.dat.book_hub.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Table(name="BookContents")
public class BookContent {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="book_content_id")
    private Long bookContentId;
    private String contentImg;
    private String contentText;
    @ManyToOne 
    @JoinColumn(name="book_id")
    private Book book;
}
