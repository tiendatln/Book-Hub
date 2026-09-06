package com.dat.book_hub.domain.entity;

import java.io.Serializable;

public class FavoriteId implements Serializable {
    private Long book;
    private Long user;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FavoriteId other)) {
            return false;
        }
        return java.util.Objects.equals(book, other.book)
                && java.util.Objects.equals(user, other.user);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(book, user);
    }
}
