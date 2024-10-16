package com.model;

import java.util.List;
import java.util.Map;

public class Categoria {
	private String nombre;
	private String tipo;
	Cat_Padre padre;
	
	// Constructor:
	public Categoria(String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.padre = null;
	}

	// Getters y Setters:
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setPadre(Cat_Padre catPadre) {
		this.padre = catPadre;
	}
	public Cat_Padre getPadre() {
		return this.padre;
	}

	
	
	
	/* 
	 Mal implementado, hacer un recorer vacío y después actuar de forma diferente en 
	 cat_padre y cat_hijo
	 */
	public void recorrerCategorias(Categoria cat, List<String> nombres) { 
        nombres.add(cat.getNombre());
        if (cat instanceof Cat_Padre) {
            Map<String, Categoria> hijos = ((Cat_Padre) cat).getHijos();
            for (Categoria hijo : hijos.values()) {
                recorrerCategorias(hijo, nombres);
            }
        }
    }
	
}
