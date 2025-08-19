package com.food.delivery;

public class PedidoService {
    private ServicioPago servicioPago;

    public PedidoService(ServicioPago servicioPago) {
        this.servicioPago = servicioPago;
    }

    public boolean procesarPago(Pedido pedido) {
        try {
            return servicioPago.realizarPago(pedido.getTotal());
        } catch (PaymentRejectedException e) {
            System.out.println("Error de pago: " + e.getMessage());
            return false;
        }
    }
}

