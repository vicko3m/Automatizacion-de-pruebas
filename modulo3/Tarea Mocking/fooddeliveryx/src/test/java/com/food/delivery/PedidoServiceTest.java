package com.food.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    ServicioPago servicioPago;

    @InjectMocks
    PedidoService pedidoService;

    // ✔️ 1. Test de pago exitoso
    @Test
    void testPagoExitoso() {
        Pedido pedido = new Pedido(1500);
        when(servicioPago.realizarPago(1500)).thenReturn(true);

        boolean resultado = pedidoService.procesarPago(pedido);

        assertTrue(resultado); // ✅ debería pasar
        verify(servicioPago).realizarPago(1500);
    }

    // ✔️ 2. Simulación de excepción
    @Test
    void testPagoRechazadoLanzaExcepcion() {
        Pedido pedido = new Pedido(999);
        when(servicioPago.realizarPago(999))
            .thenThrow(new PaymentRejectedException("Fondos insuficientes"));

        boolean resultado = pedidoService.procesarPago(pedido);

        assertFalse(resultado); // ✅ debe devolver false por el catch
        verify(servicioPago).realizarPago(999);
    }

    // ✔️ 3. Uso de ArgumentCaptor
    @Test
    void testCapturaMontoConArgumentCaptor() {
        Pedido pedido = new Pedido(1200);
        when(servicioPago.realizarPago(anyDouble())).thenReturn(true);

        pedidoService.procesarPago(pedido);

        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(servicioPago).realizarPago(captor.capture());

        assertEquals(1200, captor.getValue());
    }

    // ✔️ 4. Test específico solicitado: simular error en pago y confirmar respuesta
    @Test
    void testErrorEnPagoSimulado() {
        Pedido pedido = new Pedido(2000); // monto alto simula error

        when(servicioPago.realizarPago(2000))
            .thenThrow(new PaymentRejectedException("Error en la transacción"));

        boolean resultado = pedidoService.procesarPago(pedido);

        assertFalse(resultado); // debe devolver false por el manejo de excepción
        verify(servicioPago).realizarPago(2000);
    }
}
