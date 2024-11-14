package com.market.svcentral;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private int cant;
    private float subTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto;
    
    public Item() {
    	
    }

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

    public Proveedor getProveedor() {
        return producto.getProveedor();
    }
}