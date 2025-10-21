package com.example.AuthService.dto.request;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
