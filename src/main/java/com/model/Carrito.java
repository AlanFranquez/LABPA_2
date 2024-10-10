package com.model;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;


public class Carrito {

    public class ProductoCarrito {
        private Producto producto;
        private int cantidad;

        public ProductoCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getTotal() {
            return producto.getPrecio() * cantidad;
        }

        @Override
        public String toString() {
            return "Referencia: " + producto.getNumRef() + 
                   ", Nombre: " + producto.getNombre() + 
                   ", Descripción: " + producto.getDescripcion() +
                   ", Precio: " + producto.getPrecio() + 
                   ", Cantidad: " + cantidad + 
                   ", Total: " + getTotal();
        }
    }

    private List<ProductoCarrito> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto.getStock() >= cantidad) {
            ProductoCarrito productoCarrito = new ProductoCarrito(producto, cantidad);
            productos.add(productoCarrito);
            producto.setStock(producto.getStock() - cantidad); // Actualiza el stock disponible
        } else {
            System.out.println("No se puede agregar el producto. Stock insuficiente. Stock disponible: " + producto.getStock());
        }
    }

    public void verCarrito() {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            for (ProductoCarrito productoCarrito : productos) {
                System.out.println(productoCarrito);
            }
        }
    }

    public void generarOrdenDeCompra(int numeroOrden, int numero) {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío. No se puede generar la orden de compra.");
            return;
        }


        OrdenDeCompra orden = new OrdenDeCompra(numeroOrden);
        for (ProductoCarrito productoCarrito : productos) {
            orden.addItem(productoCarrito.getProducto(), productoCarrito.getCantidad());
        }
    }
}

