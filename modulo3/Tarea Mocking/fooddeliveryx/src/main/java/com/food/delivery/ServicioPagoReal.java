package com.food.delivery;

public class ServicioPagoReal implements ServicioPago {
    @Override
    public boolean realizarPago(double monto) {
        return monto <= 1000; // Ejemplo de regla real
    }
}
