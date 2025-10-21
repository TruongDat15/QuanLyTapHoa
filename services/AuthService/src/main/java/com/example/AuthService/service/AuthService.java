package com.example.AuthService.service;

import com.example.AuthService.dto.request.LoginRequest;
import com.example.AuthService.dto.response.LoginResponse;
import com.example.AuthService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        User user = (User) authenticate.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        return  LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
