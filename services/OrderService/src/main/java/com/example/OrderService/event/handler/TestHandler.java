package com.example.OrderService.event.handler;



import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestHandler {

    @RabbitListener(queues = "order.payment.queue")
    public void handlePaymentCompleted(Order order) {
        System.out.println("🔔 ORDER SERVICE: Received PaymentCompletedEvent for order: " + order.getOrderId());
        System.out.println("📦 Updating order status to PAID: " + order.getOrderId());

        // Update order status
        order.setStatus(OrderStatus.PAID);
        System.out.println("✅ ORDER SERVICE: Order status updated: " + order);
    }
}