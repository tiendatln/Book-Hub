package com.dat.book_hub.application.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dat.book_hub.application.dto.request.LoginRequestDto;
import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.LoginResponseDto;
import com.dat.book_hub.application.dto.response.RegisterResponseDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;
import com.dat.book_hub.application.mapping.UserMapper;
import com.dat.book_hub.application.usecase.UserUseCase;
import com.dat.book_hub.domain.entity.User;
import com.dat.book_hub.domain.repository.UserRepository;
import com.dat.book_hub.infrastructure.security.JwtService;
import com.dat.book_hub.infrastructure.security.SecurityUserDetails;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Async
    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        User newUser = this.userMapper.toEntity(registerRequestDto);
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        boolean isCreated = this.userRepository.createUser(newUser);
        if (!isCreated) {
            throw new RuntimeException("Failed to create user");
        }
        User savedUser = this.userRepository.findByUsername(newUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after creation"));
        SecurityUserDetails userDetails = new SecurityUserDetails(newUser);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        RegisterResponseDto registerResponseDto = new RegisterResponseDto(
                accessToken,
                refreshToken,
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getRole(),
                newUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );

        return registerResponseDto;
    }

    @Async
    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findUserLogin(loginRequestDto.username());
        if (this.passwordEncoder.matches(loginRequestDto.password(), user.getPassword()) == true) {
            SecurityUserDetails userDetails = new SecurityUserDetails(user);
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            LoginResponseDto loginResponseDto = new LoginResponseDto(
                    accessToken,
                    refreshToken
            );
            return loginResponseDto;
        }
        return null;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserResponseDto getUserAndBook(String username) {
        return this.userMapper.toResponseDto(this.userRepository.findByUsername(username));
    }

}
