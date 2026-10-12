package com.paymentservice.consumer;

import com.paymentservice.event.OrderCreatedEvent;
import com.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group")
    public void consumeOrderCreatedEvent(
            OrderCreatedEvent event,
            Acknowledgment acknowledgment) {

        try {

            log.info(
                    "Received Order Event : {}",
                    event.getOrderNumber());

            paymentService.processPayment(
                    event);

            acknowledgment.acknowledge();

            log.info(
                    "Offset Committed For Order : {}",
                    event.getOrderNumber());

        } catch (Exception ex) {

            log.error(
                    "Payment Processing Failed For Order : {}",
                    event.getOrderNumber(),
                    ex);

        }
    }
}