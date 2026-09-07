package com.dat.book_hub.domain.entity;

import java.io.Serializable;

public class BookTagId implements Serializable {
    private Long book;
    private Long tag;

    public BookTagId() {
    }

    public BookTagId(Long book, Long tag) {
        this.book = book;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BookTagId other)) {
            return false;
        }
        return java.util.Objects.equals(book, other.book)
                && java.util.Objects.equals(tag, other.tag);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(book, tag);
    }
}