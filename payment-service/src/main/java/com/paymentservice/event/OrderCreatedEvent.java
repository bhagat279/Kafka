package com.paymentservice.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String orderNumber;

    private String customerName;

    private String customerEmail;

    private BigDecimal amount;

    private LocalDateTime eventTime;
}