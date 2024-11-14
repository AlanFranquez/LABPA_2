package com.market.svcentral;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Categoria {
	@Id
	private String nombre;

	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "cat_padre_nombre")
	Cat_Padre padre;
	
	public Categoria() {
		
	}
	
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
