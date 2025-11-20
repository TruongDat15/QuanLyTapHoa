package com.example.OrderService.controller;

import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.OrderStaticService;
import com.example.common.dto.orderdtos.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/static")
public class OrderStaticController {

    private final OrderStaticService orderStaticService ;

    // get all orders
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderStaticService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // get all orders
    @GetMapping("/allCompleted")
    public ResponseEntity<List<OrderDTO>> getAllOrdersCompleted() {
        List<OrderDTO> orders = orderStaticService.getAllOrdersCompleted();
        return ResponseEntity.ok(orders);
    }
}