package com.example.PaymentService.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/public/webhook")
    public ResponseEntity<String> receive(@RequestBody String body) {
        System.out.println("===== SEEPAY WEBHOOK =====");
        System.out.println(body);
        return ResponseEntity.ok("OK");
    }
}
