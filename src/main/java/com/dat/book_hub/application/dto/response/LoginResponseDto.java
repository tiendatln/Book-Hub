package com.dat.book_hub.application.dto.response;

public record LoginResponseDto(
    String accessToken,
    String refreshToken
) {

}
