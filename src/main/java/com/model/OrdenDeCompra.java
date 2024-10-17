package com.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OrdenDeCompra {
    private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    private Map<Integer, Item> items;
    private String estadoOrden;

    public OrdenDeCompra(int numero) {
    	fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = 0;
        this.estadoOrden = "En preparación";
        this.items = new HashMap<>();
    }
    
    public OrdenDeCompra(Map <Integer, Item> itemsAdquiridos, float precioTotal) {
    	Random random = new Random();
    	this.items = itemsAdquiridos;
    	this.numero = random.nextInt(1000);
    	this.precioTotal = precioTotal;
    	this.fecha = LocalDateTime.now();
    	this.estadoOrden = "En preparación";
    }
    
    
    
    // Getters y Setters:
    
    public String getEstado() {
    	return this.estadoOrden;
    }
    
    public void setEstado(String estado) {
    	this.estadoOrden = estado;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public float getPrecioTotal() {
        return precioTotal;
    }
    public Map<Integer, Item> getItems() {
        return items;
    }
    
    private void setPrecioTotal() {
        float total = 0;
        if (items.isEmpty()) {
	        this.precioTotal = 0;
	    } else {
	        Collection<Item> col = items.values();
	        for (Item i : col) {
	            total += i.getSubTotal();
	        }
	        this.precioTotal = total;
	    }
    }

    public void addItem(Producto producto, int cant) {
    	if(items.containsKey(producto.getNumRef())) {
    		Item item = items.get(producto.getNumRef());
    		item.setCant(item.getCant() + cant);
    	}else {
    		Item item = new Item(cant, producto);
    		items.put(producto.getNumRef(), item);    		
    	}
    	setPrecioTotal();
    }
    public void removeItem(Integer codigo) {
        items.remove(codigo);
        setPrecioTotal();
    }
    
    public DTOrdenDeCompra crearDT() {
    	return new DTOrdenDeCompra(numero, getItems(), getPrecioTotal(), this.getEstado());
    }
    
    
}