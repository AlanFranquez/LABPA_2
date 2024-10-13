package com.model;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;


public class Carrito {

	

	private List<Item> items;

    public Carrito() {
        items = new ArrayList<>();
    }
    
    public List<Item> getProductos() {
    	return this.items;
    }

    public void agregarProducto(Item i) {
        if (i.getProducto().getStock() >= i.getCant()) {
            items.add(i);
        } else {
            System.out.println("No se puede agregar el producto. Stock insuficiente. ");
        }
    }
    
    public void vaciarCarrito() {
        items.clear();
    }
    
    public void eliminarProd(int numRef) {
    	 for (int i = 0; i < items.size(); i++) {
             if (items.get(i).getProducto().getNumRef() == numRef) {
                 items.remove(i);
                 return;
             }
         }
    }
    
    public Boolean existeProducto(int numRef) {
    	for(Item i : items) {
    		if(i.getProducto().getNumRef() == numRef) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    public void verCarrito() {
        if (items.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            for (Item productoCarrito : items) {
                System.out.println(productoCarrito.getProducto().getNombre());
            }
        }
    }
    int numeroOrden = 1;
    public int generarNumeroOrden() {
    	return numeroOrden++;
	 }

    /*public void generarOrdenDeCompra() {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío. No se puede generar la orden de compra.");
            return;
        }
        int numeroOrden = generarNumeroOrden();

        OrdenDeCompra orden = new OrdenDeCompra(numeroOrden);
        for (ProductoCarrito productoCarrito : productos) {
            orden.addItem(productoCarrito.getProducto(), productoCarrito.getCantidad());
        }
    }*/
}

