package com.example.OrderService.dto;


import com.example.OrderService.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Integer productId;
    private int quantity;
    private double price;

    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return new OrderItemDTO(
                Integer.parseInt(orderItem.getProductId()),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}
