package com.market.svcentral;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class usuarioRandom {

	@Id
	private String nombre;
	private int edad;
	
	public usuarioRandom(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getEdad() {
		return this.edad;
	}
}
