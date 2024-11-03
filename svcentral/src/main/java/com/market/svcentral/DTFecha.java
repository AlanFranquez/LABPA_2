package com.market.svcentral;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DTFecha {
	private Long id;
	private int dia;
	private int mes;
	private int anio;
	
	public DTFecha() {
		
	}
	
	public DTFecha(int dia, int mes, int anio) {
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}
	
	public int getDia() {
		return this.dia;
	}
	
	public int getAnio() {
		return this.anio;
	}
	
	public int getMes() {
		return this.mes;
	}
}
