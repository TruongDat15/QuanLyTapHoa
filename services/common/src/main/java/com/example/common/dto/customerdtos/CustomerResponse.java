package com.example.common.dto.customerdtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Integer loyaltyPoints;
    private LocalDateTime createdAt;
}