package com.example.OrderService.controller;



import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.utils.HelloUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @PostMapping("/draft")
    public ResponseEntity<OrderDTO> createDraftOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderDTO orderDTO = orderService.createDraftOrder(username);
        System.out.printf("Created draft order for user: %s%n", username);
        return ResponseEntity.ok(orderDTO);
    }


    @PutMapping("/pending")
    public ResponseEntity<OrderDTO> pendingOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.pendingOrder(orderDTO);
        return ResponseEntity.ok(updatedOrderDTO);
    }



    @GetMapping("/test")
    public String test() {
        return "Order Service is running!";
    }

    @GetMapping("/hello")
    public String helloFromShared() {
        return HelloUtil.helloWorld();
    }
}