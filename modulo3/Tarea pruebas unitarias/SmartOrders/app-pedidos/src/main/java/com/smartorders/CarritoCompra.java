
package com.smartorders;

import java.util.*;

public class CarritoCompra {
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}
