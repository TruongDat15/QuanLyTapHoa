package com.example.CustomerService.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    private String name;

    private String phone;

    private String email;
    private String address;
    // 0 = unknown, 1 = male, 2 = female
    private Integer gender;
}
