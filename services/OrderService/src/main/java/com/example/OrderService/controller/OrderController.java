package com.example.OrderService.controller;



import com.example.OrderService.service.OrderService;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.enums.PaymentStatus;
import com.example.common.utils.HelloUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


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

    // fe gửi order lên để chuyển từ draft sang pending , nhận về trạng thái processing và đợi  notification websocket
    // khi order được xử lý xong gửi trạng thái pending về cho fe

    @PostMapping("sale/{orderId}/pay")
    public ResponseEntity<OrderDTO> payOrder(@PathVariable UUID orderId) {
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        System.out.println("Processing payment for order ID ⛔: " + orderDTO.getCashierId());
        System.out.println(orderDTO);
        if (orderDTO == null) {
            return ResponseEntity.notFound().build();

        }
    //    OrderDTO updatedOrderDTO = orderService.updateOrder(orderId, PaymentStatus.PENDING);
        OrderDTO updatedOrderDTO = orderService.payOrder(orderId);
        return ResponseEntity.ok(updatedOrderDTO);
    }
    @PostMapping("sale/{orderId}/confirm")
    public ResponseEntity<OrderDTO> confirmOrder(@PathVariable UUID orderId) {
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        System.out.println("##############################"+ orderDTO);
        if (orderDTO == null) {
            return ResponseEntity.notFound().build();
        }
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderId, PaymentStatus.COMPLETED);
        // gửi sự kiện thanh toán thành công cho Notification Service va Inventory Service
        orderService.confirmOrderEvent(updatedOrderDTO);
        return ResponseEntity.ok(updatedOrderDTO);
    }
    @PostMapping("/sale/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable UUID orderId) {
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        if (orderDTO == null) {
            return ResponseEntity.notFound().build();
        }
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderId, PaymentStatus.DRAFT);
        return ResponseEntity.ok(updatedOrderDTO);
    }




    // nên nhận thông tin và gọi Giamr giữ tạm, trừ kho ở Inventory Service và đợi phản hồi
    // khi nhận được phản hồi thành công thì chuyển trạng thái đơn hàng sang completed
    // nếu thất bại thì chuyển trạng thái đơn hàng sang failed

    // hiện tại nhận được sự kiện tự động cập nhật thành công trả về client, không cần đợi kho có ok không




    @PutMapping("/completed")
    public ResponseEntity<OrderDTO> completeOrder(@RequestBody OrderDTO orderDTO) {

        // gửi sử kiện trừ kho ở Inventory Service

        // cập nhật trạng thái đơn hàng sang completed

        OrderDTO updatedOrderDTO = orderService.updateOrderStatus(orderDTO.getOrderId(), com.example.common.enums.OrderStatus.COMPLETED).orElse(null);

        // gửi sự kiện thanh toán thành công cho Notification Service
        return ResponseEntity.ok(updatedOrderDTO);
    }




    @GetMapping("/pay")
    public String test() {
        return "Order Service is running!";
    }

    @PostMapping("/sale/test/")
    public String testOrder(@RequestBody UUID orderId) {
        System.out.println("Processing payment for order ID: " + orderId);
        return "Order Service is running!";
    }
    @PostMapping("/sale/test/{orderId}")
    public String test2Order(@PathVariable UUID orderId) {
        System.out.println("Processing payment for order ID: " + orderId);
        return "Order Service is running!";
    }

    @GetMapping("/hello")
    public String helloFromShared() {
        return HelloUtil.helloWorld();
    }

}