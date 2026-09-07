package com.dat.book_hub.application.dto.response;

public record  RefreshTokenResponseDto(
    String accessToken,
    String refreshToken
) {

}
