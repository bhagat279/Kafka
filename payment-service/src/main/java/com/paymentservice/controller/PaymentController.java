package com.paymentservice.controller;

import com.paymentservice.dto.PaymentResponse;
import com.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{orderNumber}")
    public PaymentResponse getPayment(
            @PathVariable
            String orderNumber) {

        return paymentService
                .getPaymentByOrderNumber(
                        orderNumber);
    }
}