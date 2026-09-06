package com.dat.book_hub.application.dto.request;

public record RegisterRequestDto(
    String username,
    String password,
    String email
) {

}
