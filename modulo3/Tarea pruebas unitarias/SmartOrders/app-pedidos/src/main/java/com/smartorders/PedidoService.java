
package com.smartorders;

public class PedidoService {

    public double procesarPedido(CarritoCompra carrito) {
        if (carrito.estaVacio()) throw new IllegalArgumentException("Carrito vacío");

        double total = 0;
        for (Producto producto : carrito.getProductos()) {
            producto.reducirStock(1); // suponiendo 1 por producto
            total += producto.getPrecio();
        }
        return total;
    }
}


