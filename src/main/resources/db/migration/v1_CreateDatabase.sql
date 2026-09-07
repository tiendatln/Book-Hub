CREATE TABLE Users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

CREATE TABLE Books (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    thumbnail VARCHAR(255),
    url VARCHAR(255),
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL,
    user_id BIGINT,
    CONSTRAINT fk_books_user
        FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE BookContents (
    book_content_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_img VARCHAR(255),
    content_text VARCHAR(255),
    book_id BIGINT,
    CONSTRAINT fk_book_contents_book
        FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE Tags (
    tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL
);

CREATE TABLE BookTags (
    book_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, tag_id),
    CONSTRAINT fk_book_tags_book
        FOREIGN KEY (book_id) REFERENCES Books(book_id),
    CONSTRAINT fk_book_tags_tag
        FOREIGN KEY (tag_id) REFERENCES Tags(tag_id)
);

CREATE TABLE Comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comme_string VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    book_id BIGINT,
    CONSTRAINT fk_comments_book
        FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE Favorites (
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, user_id),
    CONSTRAINT fk_favorites_book
        FOREIGN KEY (book_id) REFERENCES Books(book_id),
    CONSTRAINT fk_favorites_user
        FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Ratings (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL,
    user_id BIGINT,
    book_id BIGINT,
    CONSTRAINT fk_ratings_user
        FOREIGN KEY (user_id) REFERENCES Users(user_id),
    CONSTRAINT fk_ratings_book
        FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE RefreshTokens (
    refreshToken_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

