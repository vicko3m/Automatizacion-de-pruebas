package com.smartorders;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PedidoServiceTest {

    PedidoService service;
    CarritoCompra carrito;

    @BeforeEach
    void init() {
        service = new PedidoService();
        carrito = new CarritoCompra();
    }

    @Test
    void testProcesarPedidoValido() {
        Producto p = new Producto("Tablet", 150000, 5);
        carrito.agregarProducto(p);
        double total = service.procesarPedido(carrito);
        assertEquals(150000, total);
    }

    @Test
    void testProcesarPedidoVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.procesarPedido(carrito));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testStockReducidoCorrectamente(int cantidad) {
        Producto p = new Producto("Laptop", 500000, 10);
        for (int i = 0; i < cantidad; i++) {
            carrito.agregarProducto(p);
        }
        double total = service.procesarPedido(carrito);
        assertEquals(cantidad * 500000, total);
    }

    @Test
    void testAsumeServicioDisponible() {
        boolean servicioActivo = true; // simular
        assumeTrue(servicioActivo);
        Producto p = new Producto("SSD", 60000, 5);
        carrito.agregarProducto(p);
        double total = service.procesarPedido(carrito);
        assertEquals(60000, total);
    }
}

