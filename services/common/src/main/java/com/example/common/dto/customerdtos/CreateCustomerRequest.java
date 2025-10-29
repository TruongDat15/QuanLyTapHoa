package com.example.common.dto.customerdtos;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String name;
    private String phone;
    private String email;
}