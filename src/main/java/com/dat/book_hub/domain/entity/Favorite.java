package com.dat.book_hub.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@IdClass(FavoriteId.class)
@Table(name="Favorites")
public class Favorite {
    @Id 
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    @Id 
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
