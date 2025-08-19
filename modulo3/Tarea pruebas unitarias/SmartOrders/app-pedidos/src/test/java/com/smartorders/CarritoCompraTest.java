package com.smartorders;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CarritoCompraTest {

    CarritoCompra carrito;

    @BeforeEach
    void setUp() {
        carrito = new CarritoCompra();
    }

    @Test
    void testAgregarProducto() {
        Producto p = new Producto("Monitor", 100000, 5);
        carrito.agregarProducto(p);
        assertFalse(carrito.estaVacio());
        assertEquals(1, carrito.getProductos().size());
    }

    @AfterEach
    void tearDown() {
        carrito = null;
    }
}

