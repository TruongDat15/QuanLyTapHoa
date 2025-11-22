package com.example.PaymentService.controller;


import com.example.PaymentService.service.PaymentService;
import com.example.common.dto.orderdtos.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {


    private final PaymentService paymentService;

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/public/webhook")
    public ResponseEntity<String> receive(@RequestBody String body) {
        System.out.println("===== SEEPAY WEBHOOK =====");
        System.out.println(body);
        // gư thong bao thanh công



        paymentService.publishPaymentCompleted(body);

        return ResponseEntity.ok("OK");
    }


    // nhận webhook từ seepay hoặc xác nhận từ Fe nếu là tiền mặt
    @PutMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody OrderDTO orderDTO) {
        System.out.println("===== PAYMENT CONFIRM =====");
        System.out.println(orderDTO);
        return ResponseEntity.ok("Payment Confirmed");
    }
}
