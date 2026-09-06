package com.dat.book_hub.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dat.book_hub.application.dto.request.LoginRequestDto;
import com.dat.book_hub.application.dto.request.RegisterRequestDto;
import com.dat.book_hub.application.dto.response.LoginResponseDto;
import com.dat.book_hub.application.dto.response.RegisterResponseDto;
import com.dat.book_hub.application.dto.response.UserResponseDto;
import com.dat.book_hub.application.usecase.UserUseCase;



@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> postMethodName(@RequestBody LoginRequestDto loginRequestDto) {
        //TODO: process POST request
        LoginResponseDto response = this.userUseCase.loginUser(loginRequestDto);
        if(response == null){
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponseDto> postMethodName(@RequestBody RegisterRequestDto registerRequestDto) {
        //TODO: process POST request
        RegisterResponseDto response = this.userUseCase.registerUser(registerRequestDto);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<?> getMethodName(@RequestParam String username) {
        UserResponseDto userResponseDto = this.userUseCase.getUserByUsername(username);

        return ResponseEntity.ok(userResponseDto);
    }
    
}
