package com.example.CustomerService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        System.out.println("🔐 Mongo URI = " + System.getenv("MONGO_URI"));
        return "Hello, Customer Service is up and running!";
    }
}
