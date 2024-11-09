package com.market.svcentral;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.market.svcentral.exceptions.PuntajeInvalidoException;

@Entity
public class Puntaje {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Integer valor;
	private Integer producto;
	
	public Puntaje () { };

	/**
 	* @param calificación
 	* @param numRef del producto
 	* @throws PuntajeInvalidoException
 	*/
	public Puntaje(Integer valor, Integer producto) throws PuntajeInvalidoException {
		if(valor > 0 && valor < 6) {
			this.valor = valor;
			this.producto = producto;
		}else {
			throw new PuntajeInvalidoException("El valor de la puntuación debe ser un número entre 1 y 5");
		}
	}
	
	public Integer getValor() {
		return this.valor;
	}
	
	public Integer getProducto() {
		return this.producto;
	}
	
	public void setvalor(Integer valor) throws PuntajeInvalidoException {
		if(valor > 0 && valor < 6) {
			this.valor = valor;
		}else {
			throw new PuntajeInvalidoException("El valor de la puntuación debe ser un número entre 1 y 5");
		}
	}
}