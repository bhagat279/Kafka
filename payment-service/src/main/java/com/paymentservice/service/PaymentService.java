package com.paymentservice.service;

import com.paymentservice.dto.PaymentResponse;
import com.paymentservice.entity.Payment;
import com.paymentservice.entity.PaymentStatus;
import com.paymentservice.event.OrderCreatedEvent;
import com.paymentservice.event.PaymentProcessedEvent;
import com.paymentservice.exception.PaymentNotFoundException;
import com.paymentservice.producer.PaymentProducer;
import com.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentProducer paymentProducer;

    public void processPayment(
            OrderCreatedEvent event) {

        log.info(
                "Processing Payment For Order : {}",
                event.getOrderNumber());

        Payment payment =
                Payment.builder()
                        .orderNumber(
                                event.getOrderNumber())
                        .amount(
                                event.getAmount())
                        .status(
                                PaymentStatus.SUCCESS)
                        .build();

        // Save in DB
        payment = paymentRepository.save(payment);

        log.info(
                "Payment Saved Successfully For Order : {}",
                event.getOrderNumber());

        // Publish Payment Processed Event
        PaymentProcessedEvent paymentProcessedEvent =
                PaymentProcessedEvent.builder()
                        .orderNumber(
                                payment.getOrderNumber())
                        .amount(
                                payment.getAmount())
                        .paymentStatus(
                                payment.getStatus().name())
                        .eventTime(
                                LocalDateTime.now())
                        .build();

        paymentProducer.publishPaymentProcessedEvent(
                paymentProcessedEvent);

        log.info(
                "PaymentProcessedEvent Published For Order : {}",
                payment.getOrderNumber());
    }

    // getpaymnetbyorder-number
    public PaymentResponse getPaymentByOrderNumber(
            String orderNumber) {

        Payment payment =
                paymentRepository
                        .findByOrderNumber(
                                orderNumber)
                        .orElseThrow(
                                () -> new PaymentNotFoundException(
                                        orderNumber));

        return PaymentResponse.builder()
                .id(payment.getId())
                .orderNumber(
                        payment.getOrderNumber())
                .amount(
                        payment.getAmount())
                .status(
                        payment.getStatus())
                .createdAt(
                        payment.getCreatedAt())
                .build();
    }
}