package com.example.OrderService.service;

import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTO createDraftOrder(String cashierID);


    OrderDTO pendingOrder(OrderDTO orderDTO);
    OrderDTO cancelOrder(UUID orderId);
    OrderDTO completeOrder(UUID orderId);
    OrderDTO findOrderById(UUID orderId);
    List<OrderDTO> findByStatus(OrderStatus status);
    List<OrderDTO> findAllOrders();


}
