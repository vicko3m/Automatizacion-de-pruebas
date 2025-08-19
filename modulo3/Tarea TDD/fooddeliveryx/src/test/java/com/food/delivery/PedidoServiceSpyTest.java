package com.food.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceSpyTest {

    @Spy
    ServicioPagoReal servicioPagoReal = new ServicioPagoReal();

    @InjectMocks
    PedidoService pedidoService;

    @Test
    void testConSpyMetodoRealSimulado() {
        doReturn(false).when(servicioPagoReal).realizarPago(500.0);

        boolean resultado = pedidoService.procesarPago(new Pedido(500.0));

        assertFalse(resultado);
    }
}
