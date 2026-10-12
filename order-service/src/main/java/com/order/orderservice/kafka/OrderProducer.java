package com.order.orderservice.kafka;

import com.order.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    private static final String TOPIC = "order-created";

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Publishing OrderCreatedEvent for Order Number : {}", event.getOrderNumber());

        kafkaTemplate.send(
                TOPIC,
                event.getOrderNumber(),
                event);

        log.info("OrderCreatedEvent published successfully for Order Number : {}", event.getOrderNumber());
    }
}