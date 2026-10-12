package com.order.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Customer email is required")
    private String customerEmail;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00",
            message = "Amount must be greater than 0")
    private BigDecimal amount;
}