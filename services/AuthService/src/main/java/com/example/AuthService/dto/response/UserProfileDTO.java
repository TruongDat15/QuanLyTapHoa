package com.example.AuthService.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String gender;
    String address;
    String dateOfBirth;
}
