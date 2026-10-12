package com.order.orderservice.controller;

import com.order.orderservice.dto.CreateOrderRequest;
import com.order.orderservice.dto.OrderResponse;
import com.order.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create Order",
            description = "Creates a new order and publishes OrderCreatedEvent to Kafka"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }
}