package com.dat.book_hub.application.mapping;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;
import com.dat.book_hub.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequestDto registerRequestDto);

    UserResponseDto toResponseDto(Optional<User> user);
}
