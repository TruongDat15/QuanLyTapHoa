package com.example.OrderService.controller;



import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderStatus;
import com.example.OrderService.event.publisher.OrderCreatedEvent;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
//import com.example.common.utils.HelloUtil;
import com.example.common.utils.HelloUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @PostMapping("/draft")
    public ResponseEntity<OrderDTO> createDraftOrder(@RequestBody String cashierID) {
        OrderDTO orderDTO = orderService.createDraftOrder(cashierID);
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