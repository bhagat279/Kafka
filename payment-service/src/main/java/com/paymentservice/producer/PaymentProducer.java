package com.paymentservice.producer;

import com.paymentservice.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private static final String TOPIC =
            "payment-processed";

    private final KafkaTemplate<
            String,
            PaymentProcessedEvent> kafkaTemplate;

    public void publishPaymentProcessedEvent(
            PaymentProcessedEvent event) {

        log.info(
                "Publishing PaymentProcessedEvent : {}",
                event.getOrderNumber());

        kafkaTemplate.send(
                TOPIC,
                event.getOrderNumber(),
                event);

        log.info(
                "PaymentProcessedEvent Published : {}",
                event.getOrderNumber());
    }
}