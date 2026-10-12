package com.paymentservice.dto;

import com.paymentservice.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private String orderNumber;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}