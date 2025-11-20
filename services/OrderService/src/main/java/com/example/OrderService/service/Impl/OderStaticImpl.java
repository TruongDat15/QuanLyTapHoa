package com.example.OrderService.service.Impl;

import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderItem;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderStaticService;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.dto.orderdtos.OrderItemDTO;
import com.example.common.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OderStaticImpl implements OrderStaticService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        // Lấy tất cả đơn hàng từ repository và chuyển đổi sang DTO
        return orderRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // kiểm tra và lấy tất cả đơn hàng đã hoàn thành
    @Override
    public List<OrderDTO> getAllOrdersCompleted() {
        // Tránh gọi lại repository: tận dụng kết quả đã chuyển đổi
        return getAllOrders()
                .stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    // Helper để chuyển Order -> OrderDTO (tái sử dụng)
    private OrderDTO toDto(Order order) {
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                OrderItemDTO itemDTO = OrderItemDTO.builder()
                        .OrderItemId(item.getOrderItemId())
                        .productName(item.getProductName())
                        .barcode(item.getBarcode())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subTotal(item.getPrice() * item.getQuantity())
                        .build();
                itemDTOs.add(itemDTO);
            }
        }

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .cashierId(order.getCashierId())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalAmount())
                .orderItemDTOs(itemDTOs)
                .paymentMethod(order.getPaymentMethod())
                .customerId(order.getCustomerId())
                .build();
    }
}
