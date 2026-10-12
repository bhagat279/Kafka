package com.order.orderservice.service;

import com.order.orderservice.dto.CreateOrderRequest;
import com.order.orderservice.dto.OrderResponse;
import com.order.orderservice.entity.Order;
import com.order.orderservice.event.OrderCreatedEvent;
import com.order.orderservice.kafka.OrderProducer;
import com.order.orderservice.mapper.OrderMapper;
import com.order.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProducer orderProducer;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        log.info("Creating order for customer : {}", request.getCustomerName());

        Order order = OrderMapper.toEntity(request);

        Order savedOrder = orderRepository.save(order);

        log.info("Order saved successfully : {}", savedOrder.getOrderNumber());

        OrderCreatedEvent event =
                OrderCreatedEvent.builder()
                        .orderNumber(
                                savedOrder.getOrderNumber())
                        .customerName(
                                savedOrder.getCustomerName())
                        .customerEmail(
                                savedOrder.getCustomerEmail())
                        .amount(
                                savedOrder.getAmount())
                        .eventTime(
                                LocalDateTime.now())
                        .build();

        orderProducer.publishOrderCreatedEvent(
                event);

        log.info(
                "Kafka event published : {}",
                savedOrder.getOrderNumber());

        return OrderMapper.toResponse(
                savedOrder);
    }
}