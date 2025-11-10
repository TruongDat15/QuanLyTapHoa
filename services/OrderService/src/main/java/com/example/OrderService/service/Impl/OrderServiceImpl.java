package com.example.OrderService.service.Impl;

import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderStatus;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    @Transactional
    public OrderDTO createDraftOrder(String cashierID) {
        Order order = new Order();
        order.setStatus(OrderStatus.DRAFT);
        order.setCashierId(cashierID);
        order.setTotalAmount(0.0);
        order.setOrderItems(new ArrayList<>());
        orderRepository.save(order);

        return OrderDTO.fromEntity(order);
    }

    @Override
    public OrderDTO pendingOrder(UUID orderId) {
        return null;
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
}
