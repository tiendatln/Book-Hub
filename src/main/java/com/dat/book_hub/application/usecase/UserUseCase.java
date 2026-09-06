package com.dat.book_hub.application.usecase;

import com.dat.book_hub.application.dto.request.LoginRequestDto;
import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.LoginResponseDto;
import com.dat.book_hub.application.dto.response.RegisterResponseDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;

public interface UserUseCase{

    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto);
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
    public UserResponseDto getUserByUsername(String username);
    public UserResponseDto getUserAndBook(String username);
}
