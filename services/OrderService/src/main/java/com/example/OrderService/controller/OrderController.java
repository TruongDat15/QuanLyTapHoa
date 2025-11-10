package com.example.OrderService.controller;



import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderStatus;
import com.example.OrderService.event.publisher.OrderCreatedEvent;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderCreatedEvent orderCreatedEvent;
    private final OrderService orderService;
    private final OrderRepository orderRepository;


    @PostMapping("/draft")
    public ResponseEntity<OrderDTO> createDraftOrder(@RequestBody String cashierID) {
        OrderDTO orderDTO = orderService.createDraftOrder(cashierID);
        return ResponseEntity.ok(orderDTO);
    }



    // test RabbitMQ integration
    @PostMapping
    public String createOrder(@RequestBody String customerID) {

        Order order = new Order();
        order.setCustomerId(customerID);

        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        System.out.println("🛒 ORDER SERVICE: Creating new order: " + order);

        // Publish OrderCreatedEvent
        orderCreatedEvent.publishOrderCreated(order);

        return "Order created: " + order.getOrderId() + " - Status: " + order.getStatus();
    }

    @GetMapping("/test")
    public String test() {
        return "Order Service is running!";
    }
}