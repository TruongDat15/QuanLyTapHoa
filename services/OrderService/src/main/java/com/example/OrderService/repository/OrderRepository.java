package com.example.OrderService.repository;

import com.example.OrderService.entity.Order;
import com.example.common.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCashierIdAndStatus(String cashierId, OrderStatus status);
}
