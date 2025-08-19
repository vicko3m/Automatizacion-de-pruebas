package com.smartorders;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void testReducirStockValido() {
        Producto p = new Producto("Teclado", 10000, 10);
        p.reducirStock(3);
        assertEquals(7, p.getStock());
    }

    @Test
    void testReducirStockInvalido() {
        Producto p = new Producto("Mouse", 8000, 2);
        assertThrows(IllegalArgumentException.class, () -> p.reducirStock(3));
    }
}

