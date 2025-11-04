package com.example.AuthService.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer gender;
    private String role;

//
//    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
}
