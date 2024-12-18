package com.model;

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
	private int cantidadComprada;
	List<Comentario> comentarios;
	List<String> imagenes;
	List<Reclamo> reclamos;
	
	// Constructor:
	public DtProducto(String nombre, String descripcion, float precio, Integer numRef, String especs, Proveedor prov, String cat, List<String> imagenes, Integer stock, List<Comentario> comentarios, int cantidadComprada, List<Reclamo> reclamos) {
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
		this.cantidadComprada = cantidadComprada;
		this.reclamos = reclamos;
	}
	
	// Getters:
	public String getNombre() {
		return nombre;
	}
	
	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}
	
	public int getCantidadComprada() {
		return this.cantidadComprada;
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
	
	public void agregarImagen(String img) {
		this.imagenes.add(img);
	}
	
	public List<String> getImagenes() {
		return this.imagenes;
	}
	
	public String getPrimeraImagen() {
	    if (imagenes != null && !imagenes.isEmpty()) {
	        return imagenes.get(0);
	    }
	    return null; 
	}

}
