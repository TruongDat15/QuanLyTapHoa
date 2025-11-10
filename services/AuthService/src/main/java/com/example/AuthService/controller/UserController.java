package com.example.AuthService.controller;

import com.example.AuthService.dto.request.CreateRequest;
import com.example.AuthService.dto.response.CreateResponse;
import com.example.AuthService.dto.response.UserProfileDTO;
import com.example.AuthService.entity.User;
import com.example.AuthService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;


 //   @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/create")
    public CreateResponse register(@RequestBody CreateRequest createRequest) {
        return userService.register(createRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProfiles() {
        return ResponseEntity.ok(userService.getAllProfiles());
    }


    // Xem thong tin ban than
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(){
        UserProfileDTO userProfileDTO = userService.getMyProfile();
        return ResponseEntity.ok(userProfileDTO);
    }

    // update user info
    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO> updateMyProfile(@RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updatedProfile = userService.updateMyProfile(userProfileDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, DockerTestcó";
    }

    // xoá người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            User deletedUser = userService.deleteUser(id);
            return ResponseEntity.ok(deletedUser); // trả về JSON của user đã xoá
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
