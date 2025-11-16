package com.example.OrderService.controller;



import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.utils.HelloUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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

    // auto save draft order every time an item is added or removed
    @PutMapping("/draft")
    public ResponseEntity<OrderDTO> updateDraftOrder(@RequestBody OrderDTO orderDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // ensure the DTO carries the caller as cashierId so service can validate ownership
        orderDTO.setCashierId(username);
        OrderDTO updatedOrderDTO = orderService.updateDraftOrder(orderDTO);
        return ResponseEntity.ok(updatedOrderDTO);
    }


    @PutMapping("/pending")
    public ResponseEntity<OrderDTO> pendingOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.pendingOrder(orderDTO);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @GetMapping("/drafts/me")
    public ResponseEntity<List<OrderDTO>> getMyDrafts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderDTO> drafts = orderService.findDraftsByCashier(username);
        return ResponseEntity.ok(drafts);
    }

    @DeleteMapping("/draft/{id}")
    public ResponseEntity<Void> deleteDraft(@PathVariable UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean deleted = orderService.deleteDraft(id, username);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
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