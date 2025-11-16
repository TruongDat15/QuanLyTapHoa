package com.example.OrderService.service;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    OrderDTO createDraftOrder(String cashierID);


    OrderDTO pendingOrder(OrderDTO orderDTO);
    OrderDTO cancelOrder(UUID orderId);
    OrderDTO completeOrder(UUID orderId);
    OrderDTO findOrderById(UUID orderId);
    List<OrderDTO> findByStatus(OrderStatus status);
    List<OrderDTO> findAllOrders();


    Optional<OrderDTO> updateStatus(UUID orderId, OrderStatus orderStatus);

    OrderDTO updateDraftOrder(OrderDTO orderDTO);

    // list draft orders for a cashier (username)
    List<OrderDTO> findDraftsByCashier(String cashierId);

    // delete a draft order if it belongs to cashierId and is DRAFT; returns true if deleted
    boolean deleteDraft(UUID orderId, String cashierId);
}
