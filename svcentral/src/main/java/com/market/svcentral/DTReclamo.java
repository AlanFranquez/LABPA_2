package com.market.svcentral;

import java.time.LocalDateTime;

public class DTReclamo {
	private LocalDateTime fecha;
	private String texto;
	Producto producto;
	Proveedor proveedor;
	Cliente autor;

	public DTReclamo(String texto, LocalDateTime fecha, Producto producto, Proveedor proveedor, Cliente autor) {
		this.texto = texto;
		this.fecha = fecha;
		this.producto = producto;
		this.proveedor = proveedor;
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public LocalDateTime getFecha() {
		return this.fecha;
	}
	
	public Producto getProducto() {
		return this.producto;
	}
	
	public Cliente getAutor() {
		return this.autor;
	}
	
	
	
}