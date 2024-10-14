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

    public OrdenDeCompra(int numero) {
    	fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = 0;
        this.items = new HashMap<>();
    }
    
    public OrdenDeCompra(Map <Integer, Item> itemsAdquiridos, float precioTotal) {
    	Random random = new Random();
    	this.items = itemsAdquiridos;
    	this.numero = random.nextInt(1000);
    	this.precioTotal = precioTotal;
    	this.fecha = LocalDateTime.now();
    }
    
    // Getters y Setters:
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

    public void addItem(Producto p, int cant) {
    	if(items.containsKey(p.getNumRef())) {
    		Item item = items.get(p.getNumRef());
    		item.setCant(item.getCant() + cant);
    	}else {
    		Item item = new Item(cant, p);
    		items.put(p.getNumRef(), item);    		
    	}
    	setPrecioTotal();
    }
    public void removeItem(Integer codigo) {
        items.remove(codigo);
        setPrecioTotal();
    }
    
    public DTOrdenDeCompra crearDT() {
    	return new DTOrdenDeCompra(numero, getItems(), getPrecioTotal());
    }
    
    
}