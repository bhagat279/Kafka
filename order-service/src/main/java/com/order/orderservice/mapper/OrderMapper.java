package com.order.orderservice.mapper;

import com.order.orderservice.dto.CreateOrderRequest;
import com.order.orderservice.dto.OrderResponse;
import com.order.orderservice.entity.Order;
import com.order.orderservice.entity.OrderStatus;

import java.util.UUID;

public class OrderMapper {

    private OrderMapper() {
    }

    public static Order toEntity(
            CreateOrderRequest request) {

        return Order.builder()
                .orderNumber(
                        generateOrderNumber())
                .customerName(
                        request.getCustomerName())
                .customerEmail(
                        request.getCustomerEmail())
                .amount(
                        request.getAmount())
                .status(
                        OrderStatus.CREATED)
                .build();
    }

    public static OrderResponse toResponse(
            Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(
                        order.getOrderNumber())
                .customerName(
                        order.getCustomerName())
                .customerEmail(
                        order.getCustomerEmail())
                .amount(
                        order.getAmount())
                .status(
                        order.getStatus().name())
                .build();
    }

    private static String generateOrderNumber() {

        return "ORD-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}