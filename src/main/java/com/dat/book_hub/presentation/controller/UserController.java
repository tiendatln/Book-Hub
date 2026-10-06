package com.dat.book_hub.presentation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.book_hub.application.dto.request.LoginRequestDto;
import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.DataResponse;
import com.dat.book_hub.application.dto.response.LoginResponseDto;
import com.dat.book_hub.application.dto.response.RefreshTokenResponseDto;
import com.dat.book_hub.application.dto.response.RegisterResponseDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;
import com.dat.book_hub.application.usecase.UserUseCase;
import com.dat.book_hub.infrastructure.security.DatabaseUserDetailsService;
import com.dat.book_hub.infrastructure.security.JwtService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase, JwtService jwtService, DatabaseUserDetailsService databaseUserDetailsService) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("login")
    public ResponseEntity<DataResponse<LoginResponseDto>> postMethodName(@RequestBody LoginRequestDto loginRequestDto) {
        //TODO: process POST request
        LoginResponseDto response = this.userUseCase.loginUser(loginRequestDto);
        if (response == null) {
            return ResponseEntity.status(401).body(new DataResponse<LoginResponseDto>("Login failed!", null));
        }
        ResponseCookie cookie = ResponseCookie.from("refresh_token", response.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/user/refresh")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new DataResponse<LoginResponseDto>("Login success!", response));
    }

    @PostMapping("register")
    public ResponseEntity<DataResponse<RegisterResponseDto>> postMethodName(@RequestBody RegisterRequestDto registerRequestDto) {
        //TODO: process POST request
        RegisterResponseDto response = this.userUseCase.registerUser(registerRequestDto);
        if (response == null) {
            return ResponseEntity.status(401).body(new DataResponse<RegisterResponseDto>("Login failed!", null));
        }
        ResponseCookie cookie = ResponseCookie.from("refresh_token", response.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/user/refresh")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new DataResponse<RegisterResponseDto>("Login success!", response));
    }

    @GetMapping("/user-username")
    public ResponseEntity<?> getMethodName(@RequestParam String username) {
        UserResponseDto userResponseDto = this.userUseCase.getUserByUsername(username);

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> postMethodName(@CookieValue("refresh_token") String refreshToken) {
        //TODO: process POST request

        

        RefreshTokenResponseDto refreshTokenResponseDto = this.userUseCase.refresh(refreshToken);
        if (refreshTokenResponseDto == null) {
            return ResponseEntity.status(404).body(new DataResponse<>("No Entity response!", refreshTokenResponseDto));
        }

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshTokenResponseDto.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/user/refresh")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new DataResponse<>("Refresh success!", refreshTokenResponseDto));
    }

}
