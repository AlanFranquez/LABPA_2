package com.market.svcentral;

public class DTFecha {
	private int dia;
	private int mes;
	private int anio;
	
	
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
