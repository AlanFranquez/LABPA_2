package com.model;

import java.util.HashMap;
import java.util.Map;

public class Cat_Padre extends Categoria{
	private Map<String, Categoria> hijos;
	Cat_Padre padre;
	
	// Constructor:
	public Cat_Padre(String nombre) {
		super(nombre, "Padre");
		padre = null;
		hijos = new HashMap<String, Categoria>();
	}
	
	public String obtenerPadre() {
		if(this.padre.getNombre().isEmpty()) {
			return null;
		}
		return this.padre.getNombre();
	}
	
	// Getter:
	public Map<String, Categoria> getHijos() {
		return hijos;
	}
	
	public void setPadre(Cat_Padre catPadre) {
		this.padre = catPadre;
	}
	
	
 	public DTCat_Padre crearDT() {
 		return new DTCat_Padre(this.getNombre());
 	}
	
	public boolean verificarSiYaEsHijo(String nombre) {
		return hijos.get(nombre) != null;
	}

	// Funcionalidad:
	public void agregarHijo(Categoria cat) {
		hijos.put(cat.getNombre(), cat);
	}
	
	public Cat_Padre traerPadre() {
		
		return this.padre;
	}

}
 