package com.dat.book_hub.application.dto.response;

import java.time.LocalDateTime;

public record RegisterResponseDto(
    String accessToken,
    String refreshToken,
    String username,
    String email,
    String role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
