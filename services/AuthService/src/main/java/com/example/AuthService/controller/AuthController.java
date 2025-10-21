package com.example.AuthService.controller;

import com.example.AuthService.dto.request.LoginRequest;
import com.example.AuthService.dto.response.LoginResponse;
import com.example.AuthService.service.AuthService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor


public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        log.info("Login attempt for user: {}", request.getUsername());
        return authService.login(request);
    }

    @GetMapping("/hello")
    public String hello() {

        log.info("truy cap thanh cong");
        return "Hello, Login!";
    }

    // Them endpoint reset mat khau, gui email xac nhan, etc.
}
