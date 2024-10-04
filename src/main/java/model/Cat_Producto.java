package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Cat_Producto extends Categoria{
private Map<Integer, Producto> productos;
Cat_Padre padre;
	
	// Constructor:
	public Cat_Producto(String nombre) {
		super(nombre, "Producto");
		padre = null;
		productos = new HashMap<Integer, Producto>();
	}
	
	// Funcionalidad:
	public List<DtProducto> listarProductos() {
	    	List<DtProducto> listaProds = new ArrayList<DtProducto>();
	        
	        for(Entry<Integer, Producto> entry: this.productos.entrySet()) {
	        	Producto p = entry.getValue();
	        	
	        	DtProducto dtp = p.crearDT();
	        	listaProds.add(dtp);
	        }
	        return listaProds;
	    
	}
	
	public Cat_Padre getPadre() {
		if(this.padre == null) {
			return null;
		}
		return this.padre;
	}
	
	public String obtenerPadre() {
		if(this.padre == null) {
			return null;
		}
		return this.padre.getNombre();
	}
	
	public void setPadre(Cat_Padre cat) {
		this.padre = cat;
	}
	
	
	// Esto para que?
	// Debería de bastar con obtener los dt de los productos
	// Hay que tener cuidado de no romper el principio de las capas
	public Map<Integer, Producto> getProductos(){
		return this.productos;
	}
	
	public Producto getProducto(Integer intProd) {
		return this.productos.get(intProd);
	}

	public void agregarProducto(Producto prod) {
		productos.put(prod.getNumRef(), prod);
	}
	
	public boolean verificarProducto(int numReferencia, String titulo) {
	    if (this.productos.containsKey(numReferencia)) {
	        return false; 
	    }
	    
	  
	    for (Producto p : this.productos.values()) {
	        if (p.getNombre().equals(titulo)) {
	            return false;
	        }
	    }
	    
	 
	    return true;
	}
	
	
}