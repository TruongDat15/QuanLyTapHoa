package com.example.PaymentService.service;

import com.example.PaymentService.entity.Payment;
import com.example.PaymentService.event.publisher.PaymentPublisher;
import com.example.PaymentService.repository.PaymentRepository;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.dto.paymentdtos.PaymentResultDTO;
import com.example.common.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentPublisher paymentPublisher;

    // cập nhật trang thái thanh toán và bắn sự kiện cho kho rôi sau đó mới bắn sự kiện cho order
    public Payment confirmPayment(OrderDTO orderDTO) {
//        Optional<Payment> optionalPayment = paymentRepository.findByOrderId(orderDTO.getOrderId());
//
//        if (optionalPayment.isPresent()) {
//            Payment payment = optionalPayment.get();

            Payment payment = new Payment();

            // Cập nhật trạng thái thanh toán dựa trên phương thức thanh toán
            switch (orderDTO.getPaymentMethod()) {
                case CASH:
                    payment.setStatus(PaymentStatus.COMPLETED);
                    payment.setTransactionId(UUID.randomUUID().toString());
                    log.info("THANH TOAN TIEN MAT");

                    // gửi sự kiện thanh toán hoàn tất trừ kho

                    break;
                case BANK:
                    payment.setStatus(PaymentStatus.PENDING);
                    payment.setTransactionId(UUID.randomUUID().toString());
                    log.info("THANH TOAN QRCODE - CHO XAC NHAN");
                    break;
                case WALLET:
                    payment.setStatus(PaymentStatus.PENDING);
                    log.info("THANH TOAN VI DIEN TU - THAT BAI");
                    break;
                default:
                    break;
            }

            paymentRepository.save(payment);
            return payment;

//        } else {
//            log.error("Payment not found for Order ID: " + orderDTO.getOrderId());
//            throw new RuntimeException("Payment not found for Order ID: " + orderDTO.getOrderId());
//        }
    }

    public void publishPaymentCompleted(String ok) {

        paymentPublisher.publishPaymentCompletedEvent(ok);

    }
}
