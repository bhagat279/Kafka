package com.paymentservice.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessedEvent {

    private String orderNumber;

    private BigDecimal amount;

    private String paymentStatus;

    private LocalDateTime eventTime;
}