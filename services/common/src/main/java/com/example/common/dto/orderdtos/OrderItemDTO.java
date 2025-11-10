package com.example.common.dto.orderdtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private UUID OrderItemId;
    private String productName;
    private String barcode;
    private int quantity;
    private double price;
    private double subTotal;


//    public static OrderItemDTO fromEntity(OrderItem orderItem) {
//        return new OrderItemDTO(
//                orderItem.getOrderItemId(),
//                orderItem.getProductName(),
//                orderItem.getBarcode(),
//                orderItem.getQuantity(),
//                orderItem.getPrice()
//        );
//    }
}
