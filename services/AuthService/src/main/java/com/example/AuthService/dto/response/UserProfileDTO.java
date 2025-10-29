package com.example.AuthService.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserProfileDTO {
    String username;
    String fullName;
    String email;
    String phoneNumber;
    String role;
    Integer gender;
    String address;


    LocalDate dateOfBirth;
}
