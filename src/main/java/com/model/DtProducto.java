package com.model;

import java.io.File;
import java.util.List;

/**
 * String nombre, descripcion, especificaciones, categorias
 * <br />
 * String nickProveedor, nomProveedor
 * <br />
 * float precio
 * <br />
 * Integer numRef, stock
 * <br />
 * List <File> imagenes
 */
public class DtProducto {
	private String nombre, descripcion;
	private float precio;
	private Integer numRef, stock;
	private String especs;
	private String nickProveedor, nomProveedor;
	private String categorias;
	List<Comentario> comentarios;
	List<File> imagenes;
	
	// Constructor:
	public DtProducto(String nombre, String descripcion, float precio, Integer numRef, String especs, Proveedor prov, String cat, List<File> imagenes, Integer stock, List<Comentario> comentarios) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.numRef = numRef;
		this.especs = especs;
		this.nickProveedor = prov.getNick();
		this.nomProveedor = prov.getNombre();
		this.categorias = cat;
		this.imagenes = imagenes;
		this.stock = stock;
		this.comentarios = comentarios;
	}
	
	// Getters:
	public String getNombre() {
		return nombre;
	}
	
	public List<Comentario> getComentarios() {
		return this.comentarios;
	}
	
	public String getNombreProveedor() {
		return this.nomProveedor;
	}
	
	public String getNicknameProveedor(){
		return this.nickProveedor;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public float getPrecio() {
		return precio;
	}
	public Integer getNumRef() {
		return numRef;
	}
	public Integer getStock() {
		return stock;
	}
	public String getEspecs() {
		return especs;
	}
	
	public String getCategorias() {
		return categorias;
	}
	
	public void agregarImagen(File img) {
		this.imagenes.add(img);
	}
	
	public List<File> getImagenes() {
		return this.imagenes;
	}

}
