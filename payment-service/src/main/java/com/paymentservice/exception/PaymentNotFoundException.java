package com.paymentservice.exception;

public class PaymentNotFoundException
        extends RuntimeException {

    public PaymentNotFoundException(
            String orderNumber) {

        super(
                "Payment not found for order : "
                        + orderNumber);
    }
}