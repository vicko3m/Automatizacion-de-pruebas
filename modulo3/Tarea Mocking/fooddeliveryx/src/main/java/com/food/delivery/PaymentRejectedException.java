package com.food.delivery;

public class PaymentRejectedException extends RuntimeException {
    public PaymentRejectedException(String mensaje) {
        super(mensaje);
    }
}

