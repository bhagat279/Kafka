package com.order.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private String customerName;

    private String customerEmail;

    private BigDecimal amount;

    private String status;
}