package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Cat_Producto extends Categoria{
private Map<Integer, Producto> productos;
	
	// Constructor:
	public Cat_Producto(String nombre) {
		super(nombre, "Producto");
		productos = new HashMap<Integer, Producto>();
	}
	
	// Funcionalidad:
	public List<DtProducto> listarProductos() {
	    List<DtProducto> listaProds = new ArrayList<DtProducto>();
	        
	    for (Entry<Integer, Producto> entry: this.productos.entrySet()) {
	    	Producto producto = entry.getValue();
	        	
	    	DtProducto dtProducto = producto.crearDT();
	    	listaProds.add(dtProducto);
	    }
	    return listaProds;
	}
	
	public Map<Integer, Producto> getProductos(){
		return this.productos;
	}
	
	public Producto getProducto(Integer intProd) {
		return this.productos.get(intProd);
	}

	public void agregarProducto(Producto producto) {
		productos.put(producto.getNumRef(), producto);
	}
	
	public boolean verificarProducto(int numReferencia, String titulo) {
	    if (this.productos.containsKey(numReferencia)) {
	        return false; 
	    }
	  
	    for (Producto producto : this.productos.values()) {
	        if (producto.getNombre().equals(titulo)) {
	            return false;
	        }
	    }
	    return true;
	}
	
}