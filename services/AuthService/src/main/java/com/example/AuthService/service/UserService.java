package com.example.AuthService.service;

import com.example.AuthService.dto.request.CreateRequest;
import com.example.AuthService.dto.response.CreateResponse;
import com.example.AuthService.dto.response.UserProfileDTO;
import com.example.AuthService.entity.User;
import com.example.AuthService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Admin tạo người dùng mới
    public CreateResponse register(CreateRequest createRequest) {
        // Kiểm tra xem người dùng đã tồn tại chưa
        if (userRepository.findByUsername(createRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /* Tạo người dùng mới với mật khẩu đã mã hóa */
        User user = User.builder()
                .username(createRequest.getUsername())
                .password(passwordEncoder.encode(createRequest.getPassword()))
                .role("ROLE_USER") // Mặc định là ROLE_USER, có thể thay đổi theo yêu cầu
                .fullName(createRequest.getFullName())
                .email(createRequest.getEmail())
                .phoneNumber(createRequest.getPhoneNumber())
                .address(createRequest.getAddress())
                .gender(createRequest.getGender())
                .dateOfBirth(createRequest.getDateOfBirth())
                .build();
        userRepository.save(user);

        return CreateResponse.builder()
                .username(user.getUsername())
                .build();
    }

    // Xem thong tin ban than
    public UserProfileDTO getMyProfile(){
        // Lấy thông tin người dùng hiện tại từ SecurityContext
        String currentUsername = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileDTO.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .gender(user.getGender())
                .build();
    }

    // Cập nhật thông tin cá nhân
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN')")
    public UserProfileDTO updateMyProfile(UserProfileDTO userProfileDTO){
        // Lấy thông tin người dùng hiện tại từ SecurityContext
        Authentication authen = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String currentUsername = authen.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(userProfileDTO.getFullName());
        user.setEmail(userProfileDTO.getEmail());
        user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        user.setAddress(userProfileDTO.getAddress());
        user.setDateOfBirth(userProfileDTO.getDateOfBirth());
        user.setGender(userProfileDTO.getGender());
        user.setUpdatedProfile(true);
        userRepository.save(user);

        return UserProfileDTO.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .build();
        }

    public List<UserProfileDTO> getAllProfiles() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserProfileDTO.builder()
                        .username(user.getUsername())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .dateOfBirth(user.getDateOfBirth())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
