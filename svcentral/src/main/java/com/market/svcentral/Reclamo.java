package com.market.svcentral;

import java.time.LocalDateTime;

public class Reclamo {
	private LocalDateTime fecha;
	private String texto;
	Producto producto;
	Proveedor proveedor;
	Cliente autor;

	public Reclamo(String texto, LocalDateTime fecha, Producto producto, Proveedor proveedor, Cliente autor) {
		this.texto = texto;
		this.fecha = fecha;
		this.producto = producto;
		this.proveedor = proveedor;
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	public DTReclamo crearDT() {
		return new DTReclamo(this.texto, this.fecha, this.producto, this.proveedor, this.autor);
	}
}