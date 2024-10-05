package com.model;

public class Item {
    private int cant;
    private float subTotal;
    private Producto producto;

    public Item(int cant, Producto prod) {
        this.cant = cant;
        this.producto = prod;
        this.subTotal = prod.getPrecio() * cant;
    }
    //getts y setts
    public int getCant() {
        return cant;
    }
    public void setCant(int cant) {
        this.cant = cant;
        this.subTotal = cant * this.producto.getPrecio();
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto prod) {
        this.producto = prod;
        this.subTotal = this.cant * prod.getPrecio();
    }
    public float getSubTotal() {
        return subTotal;
    }
    
    
    public DTItem crearDT() {
    	return new DTItem(this.getCant(), this.getProducto());
    }
}
