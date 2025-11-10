package com.example.PaymentService.event;


import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderEventDTO implements Serializable {
    private String orderId;
    private String productName;
    private int quantity;
    private String status;

    @Override
    public String toString() {
        return "OrderEventDTO{orderId='" + orderId + "', productName='" + productName + "', quantity=" + quantity + ", status='" + status + "'}";
    }
}