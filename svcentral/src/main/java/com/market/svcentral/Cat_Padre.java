package com.market.svcentral;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cat_Padre extends Categoria{
	@OneToMany(mappedBy = "padre", cascade = CascadeType.PERSIST)
	private Map<String, Categoria> hijos;
	
	// Constructor:
	public Cat_Padre(String nombre) {
		super(nombre, "Padre");
		hijos = new HashMap<String, Categoria>();
	}
	
	public Cat_Padre() {
		
	}
	
	// Getter:
	public Map<String, Categoria> getHijos() {
		return hijos;
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
}
 