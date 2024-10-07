package com.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTOrdenDeCompra {
	private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    private Map<Integer, Item> items;

    public DTOrdenDeCompra(int numero, Map <Integer, Item>items, float precioTotal) {
    	fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = precioTotal;
        this.items = items;
    }
    
    
    public List<DTItem> listarItems() {
    	List <DTItem> listarItems = new ArrayList<>();
    	
    	
    	for(Map.Entry<Integer, Item> entry : this.items.entrySet()) {
    		Item it = entry.getValue();
    		
    		listarItems.add(it.crearDT());
    	}
    	
    	return listarItems;
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
    
    public String getFechaString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	return fecha.format(formatter).toString();
    }


	@Override
	public String toString() {
		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String items = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Item item : this.items.values()) {
			items = items + tab + tab + item.getProducto().getNombre() + ": " + item.getCant() + "<br />"; 
		}
		return "<html>Orden número = " + numero + "<br />" + tab + "precioTotal = " + precioTotal + "<br />" + tab + "fecha = "
				+ fecha.format(formatter).toString() + "<br />" + tab + "items:<br />" + items + "</html>";
	}
    
}
