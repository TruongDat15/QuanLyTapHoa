package com.example.AuthService.config;


import com.example.AuthService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class DataInitializer {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            // Initialize data here if needed
            if(userRepository.findByUsername("admin").isEmpty()){
                var adminUser = new com.example.AuthService.entity.User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin")); // {noop} indicates no password encoding
                adminUser.setRole("ROLE_ADMIN");
                adminUser.setEmail("admin@gmail.com");
                userRepository.save(adminUser);
                log.warn("Initialized admin user with username 'admin' and password 'admin', please change the password after first login.");
            }
        };
    }
}