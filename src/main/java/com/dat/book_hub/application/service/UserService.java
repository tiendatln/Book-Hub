package com.dat.book_hub.application.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dat.book_hub.application.dto.request.LoginRequestDto;
import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.LoginResponseDto;
import com.dat.book_hub.application.dto.response.RefreshTokenResponseDto;
import com.dat.book_hub.application.dto.response.RegisterResponseDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;
import com.dat.book_hub.application.mapping.UserMapper;
import com.dat.book_hub.application.usecase.UserUseCase;
import com.dat.book_hub.domain.entity.RefreshToken;
import com.dat.book_hub.domain.entity.User;
import com.dat.book_hub.domain.repository.TokenRepository;
import com.dat.book_hub.domain.repository.UserRepository;
import com.dat.book_hub.infrastructure.security.DatabaseUserDetailsService;
import com.dat.book_hub.infrastructure.security.JwtService;
import com.dat.book_hub.infrastructure.security.SecurityUserDetails;

import io.jsonwebtoken.Claims;

@Service
public class UserService implements UserUseCase {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserMapper userMapper, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * @param: RegisterRequestDto register service to hash password, create new
     * user and generate AccessToken and RefreshToken
     */
    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        User newUser = this.userMapper.toEntity(registerRequestDto);
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        User user = this.userRepository.createUser(newUser);
        if (user == null) {
            return null;
        }

        SecurityUserDetails userDetails = new SecurityUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        RefreshToken nToken = new RefreshToken(null, refreshToken, null, null, user);

        Optional<RefreshToken> token = this.tokenRepository.createRefreshToken(nToken);

        if (token.isEmpty()) {
            return null;
        }

        RegisterResponseDto registerResponseDto = new RegisterResponseDto(
                accessToken,
                refreshToken,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return registerResponseDto;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        User user = this.userRepository.findUserLogin(loginRequestDto.username());
        if (user == null) {
            return null;
        }

        if (this.passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            SecurityUserDetails userDetails = new SecurityUserDetails(user);
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            LoginResponseDto loginResponseDto = new LoginResponseDto(
                    accessToken,
                    refreshToken
            );

            RefreshToken nToken = new RefreshToken(null, refreshToken, null, null, user);

            Optional<RefreshToken> token = this.tokenRepository.createRefreshToken(nToken);
            if (token.isEmpty()) {
                return null;
            }

            return loginResponseDto;
        }
        return null;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        log.info("[UserService] getUserByUsername - username={}", username);
        Optional<User> optionalUser = this.userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(
                () -> new RuntimeException("User not found")
        );

        UserResponseDto userResponseDto
                = this.userMapper.toUserResponseDto(optionalUser);

        log.info("[UserService] getUserByUsername - value={}", user.getUsername() + user.getRole());

        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserAndBook(String username) {
        return this.userMapper.toUserResponseDto(this.userRepository.getUserAndBookByUsername(username));
    }


    /*
        @Param: refreshToken
        validate refreshToken if not valid create new refreshToken and save to database
     */
    @Override
    public RefreshTokenResponseDto refresh(String refreshToken) {

        String username = this.jwtService.extractUsername(refreshToken);
        Claims reClaims = this.jwtService.extractAllClaims(refreshToken);
        Optional<RefreshToken> rToken = this.tokenRepository.getRefreshTokenByToken(refreshToken);

        if (this.jwtService.isRefreshTokenValid(refreshToken, username) == true && rToken.isPresent()) {
            log.info("[RefreshToken] refresh - refreshToken is valid={}", refreshToken);
            DatabaseUserDetailsService databaseUserDetailsService = new DatabaseUserDetailsService(userRepository);

            UserDetails userDetails = databaseUserDetailsService.loadUserByUsername(username);

            String newAccessToken = this.jwtService.generateToken(userDetails);
            if (reClaims.getIssuedAt().after((new Date()))) {
                String newRefreshToken = this.jwtService.generateRefreshToken(userDetails);
                RefreshToken nToken = new RefreshToken(rToken.get().getRefreshTokenId(), newRefreshToken, null, null, rToken.get().getUser());

                Optional<RefreshToken> updatedToken = this.tokenRepository.updateRefreshToken(nToken, rToken.get().getRefreshTokenId());

                if (updatedToken.isEmpty()) {
                    return null;
                }
                return new RefreshTokenResponseDto(newAccessToken,
                        newRefreshToken);
            }

            return new RefreshTokenResponseDto(newAccessToken,
                    refreshToken);
        }

        return null;
    }

}
