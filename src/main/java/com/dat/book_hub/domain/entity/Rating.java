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
@Table(name="Ratings")
public class Rating {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="rating_id")
    private Long ratingId;
    @Column(nullable=false)
    private int rating;
    @ManyToOne 
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne 
    @JoinColumn(name="book_id")
    private Book book;
}
