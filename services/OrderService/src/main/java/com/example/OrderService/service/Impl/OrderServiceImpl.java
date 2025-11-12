package com.example.OrderService.service.Impl;

import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderItem;
import com.example.OrderService.event.publisher.OrderCreatedEvent;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreatedEvent orderCreatedEvent;
    private final RabbitTemplate rabbitTemplate;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderDTO createDraftOrder(String cashierID) {

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
                .build();
    }

    @Transactional
    @Override
    public OrderDTO pendingOrder(OrderDTO orderDTO) {

        log.info("Processing pending order: {}", orderDTO.getOrderId());
        Order order = orderRepository.findById(orderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
 //       order.setStatus(OrderStatus.PENDING);
        Double total = 0.0;
        for( OrderItemDTO itemDTO : orderDTO.getOrderItemDTOs()) {

            OrderItem item = OrderItem.builder()
                    .productName(itemDTO.getProductName())
                    .barcode(itemDTO.getBarcode())

                    .quantity(itemDTO.getQuantity())
                    .price(itemDTO.getPrice())
                    .order(order)
                    .build();
            itemDTO.setSubTotal(itemDTO.getPrice() * itemDTO.getQuantity());
            log.info("Order Item Subtotal: {}", itemDTO.getSubTotal());

            orderItemRepository.save(item);
            total += itemDTO.getSubTotal();
        }


        order.setTotalAmount(total);
        orderDTO.setTotalPrice(total);
        orderRepository.save(order);

        System.out.println("✅ ORDER SERVICE: Order pending with ID " + order.getOrderId());
        rabbitTemplate.convertAndSend(RabbitConstants.ORDER_EXCHANGE, RabbitConstants.ORDER_CREATED_KEY, orderDTO);
        return OrderDTO.builder()
                .status(order.getStatus())
                .totalPrice(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
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
        return Optional.empty();
    }


}
