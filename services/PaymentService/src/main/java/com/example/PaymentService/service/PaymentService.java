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

    public Payment processPayment(OrderDTO orderDTO) {

        Payment payment = Payment.builder()
                .orderId(orderDTO.getOrderId())
                .paymentMethod(orderDTO.getPaymentMethod())
                .amount(orderDTO.getTotalPrice())
                .createDate(LocalDate.now())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();

        // lấy phương thức thanh toán và xử lý
        switch (orderDTO.getPaymentMethod()) {
            case CASH:
                payment.setStatus(PaymentStatus.COMPLETED);
                payment.setTransactionId(UUID.randomUUID().toString());
                log.info("THANH TOAN TIEN MAT");
                break;
            case QR_CODE:
                payment.setStatus(PaymentStatus.PENDING);
                payment.setTransactionId(UUID.randomUUID().toString());
                break;
            default:
                break;
        }

        return paymentRepository.save(payment);
    }

    public void publishPaymentCompleted(Payment payment) {
        // chuyển payment sang dto
        PaymentResultDTO paymentResultDTO = PaymentResultDTO.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .createDate(payment.getCreateDate())
                .updateDate(payment.getUpdateDate())
                .paymentReference(payment.getPaymentReference())
                .paymentNote(payment.getPaymentNote())
                .build();

        paymentPublisher.publishPaymentCompletedEvent(paymentResultDTO);

    }
}
