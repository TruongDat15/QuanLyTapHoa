package com.example.OrderService.service.Impl;

import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderItem;
import com.example.OrderService.repository.OrderItemRepository;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import com.example.common.constrants.RabbitConstants;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.dto.orderdtos.OrderItemDTO;
import com.example.common.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static com.example.common.constrants.CustomerConstants.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderDTO createDraftOrder(String cashierID) {

        if (cashierID == null || cashierID.isBlank()) {
            throw new IllegalArgumentException("cashierID is required");
        }

        Order order = new Order();
        order.setStatus(OrderStatus.DRAFT);
        order.setCashierId(cashierID);
        order.setTotalAmount(0.0);
        order.setOrderItems(new ArrayList<>());
        orderRepository.save(order);

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .cashierId(order.getCashierId())
                .createdAt(order.getCreatedAt())
                .totalPrice(0.0)
                .orderItemDTOs(new ArrayList<>())
                .build();
    }

    @Transactional
    @Override
    public OrderDTO pendingOrder(OrderDTO orderDTO) {

        log.info("Processing pending order: {}", orderDTO.getOrderId());
        Order order = orderRepository.findById(orderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // --- Thêm check trạng thái draft ---
        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new IllegalStateException(
                    "Only draft orders can be moved to pending. Current status: " + order.getStatus()
            );
        }


        double total = 0.0;

        if (orderDTO.getCustomerId() == null || orderDTO.getCustomerId().isBlank()) {
            orderDTO.setCustomerId(DEFAULT_CUSTOMER_ID);
        }
        order.setCustomerId(orderDTO.getCustomerId());
        List<OrderItem> pendingItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : orderDTO.getOrderItemDTOs()) {

            OrderItem item = OrderItem.builder()
                    .productName(itemDTO.getProductName())
                    .barcode(itemDTO.getBarcode())
                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .order(order)
                    .build();
            itemDTO.setSubTotal(itemDTO.getPrice() * itemDTO.getQuantity());
            log.info("Order Item Subtotal: {}", itemDTO.getSubTotal());

            pendingItems.add(item);
            total += itemDTO.getSubTotal();
        }

        // Không gọi save cho items riêng lẻ — dùng cascade của Order (CascadeType.ALL) để persist/merge items khi save order

        // update orderItems collection an toàn
        order.getOrderItems().clear();       // xóa các item cũ
        order.getOrderItems().addAll(pendingItems); // thêm các item mới

        order.setTotalAmount(total);
        orderDTO.setTotalPrice(total);
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        // chỉ save order một lần để tránh nhiều lần cập nhật tăng version
        orderRepository.save(order);

        log.info("✅ ORDER SERVICE: Xử lí trạng thái nhận danh sách đơn hàng {}", order.getOrderId());
        rabbitTemplate.convertAndSend(RabbitConstants.ORDER_EXCHANGE, RabbitConstants.ORDER_CREATED_KEY, orderDTO);
        return OrderDTO.builder()
                .status(order.getStatus())
                .totalPrice(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .paymentMethod(order.getPaymentMethod())
                .orderId(order.getOrderId())
                .cashierId(order.getCashierId())
                .customerId(order.getCustomerId())
        //        .orderItemDTOs(orderDTO.getOrderItemDTOs())
                .build();
    }

    @Override
    public OrderDTO cancelOrder(UUID orderId) {

        return null;
    }

    @Override
    public OrderDTO completeOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDTO findOrderById(UUID orderId) {
        return null;
    }

    @Override
    public List<OrderDTO> findByStatus(OrderStatus status) {
        return List.of();
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        return List.of();
    }

    @Override
    public Optional<OrderDTO> updateStatus(UUID orderId, OrderStatus orderStatus) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus(orderStatus);
            orderRepository.save(order);
            return Optional.of(OrderDTO.builder()
                    .orderId(order.getOrderId())
                    .status(order.getStatus())
                    .cashierId(order.getCashierId())
                    .createdAt(order.getCreatedAt())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public OrderDTO updateDraftOrder(OrderDTO orderDTO) {

        if (orderDTO == null || orderDTO.getOrderId() == null) {
            throw new IllegalArgumentException("OrderDTO or orderId is null");
        }

        Order order = orderRepository.findById(orderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new IllegalArgumentException("Only draft orders can be updated");
        }

        // ownership check: caller must be athe cashier who creted the draft
        if (orderDTO.getCashierId() == null || !orderDTO.getCashierId().equals(order.getCashierId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to update this draft");
        }

        // remove existing items using orphanRemoval by clearing the collection — không save tạm để tránh tăng version nhiều lần
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        } else {
            order.getOrderItems().clear();
        }

        double total = 0.0;
        List<OrderItem> items = new ArrayList<>();

        if (orderDTO.getOrderItemDTOs() != null && !orderDTO.getOrderItemDTOs().isEmpty()) {
            for (OrderItemDTO it : orderDTO.getOrderItemDTOs()) {
                // OrderItemDTO uses primitive types for quantity/price, so no null check
                int qty = it.getQuantity();
                double price = it.getPrice();
                double sub = qty * price;
                it.setSubTotal(sub);

                OrderItem item = OrderItem.builder()
                        .productName(it.getProductName())
                        .barcode(it.getBarcode())
                        .quantity(qty)
                        .price(price)
                        .order(order)
                        .build();
                items.add(item);
                total += sub;
            }

            // không gọi orderItemRepository.saveAll(items) — dùng cascade
            order.setOrderItems(items);
        } else {
            order.setOrderItems(new ArrayList<>());
        }

        order.setTotalAmount(total);
        // optionally update payment method/cashier if provided
        if (orderDTO.getPaymentMethod() != null) {
            order.setPaymentMethod(orderDTO.getPaymentMethod());
        }

        // save order 1 lần
        orderRepository.save(order);

        // reflect computed total back to DTO for FE
        orderDTO.setTotalPrice(total);

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .cashierId(order.getCashierId())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalAmount())
                .orderItemDTOs(orderDTO.getOrderItemDTOs())
                .paymentMethod(order.getPaymentMethod())
                .build();
    }


    @Override
    public List<OrderDTO> findDraftsByCashier(String cashierId) {
        List<Order> drafts = orderRepository.findByCashierIdAndStatus(cashierId, OrderStatus.DRAFT);
        List<OrderDTO> dtos = new ArrayList<>();
        for (Order o : drafts) {
            OrderDTO dto = OrderDTO.builder()
                    .orderId(o.getOrderId())
                    .status(o.getStatus())
                    .cashierId(o.getCashierId())
                    .createdAt(o.getCreatedAt())
                    .totalPrice(o.getTotalAmount())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public boolean deleteDraft(UUID orderId, String cashierId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return false;
        Order order = optionalOrder.get();
        if (order.getStatus() != OrderStatus.DRAFT) return false;
        if (order.getCashierId() == null || !order.getCashierId().equals(cashierId)) return false;
        orderRepository.delete(order);
        return true;
    }

}
