package com.market.svcentral;

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
	private List<Comentario> comentarios;
	private List<String> imagenes;
	private List<Reclamo> reclamos;
	private List <Puntaje> puntajes;
	
	// Constructor:
	public DtProducto(String nombre, String descripcion, float precio, Integer numRef, String especs, Proveedor prov, String cat, List<String> imagenes, Integer stock, List<Comentario> comentarios, int cantidadComprada, List<Reclamo> reclamos, List<Puntaje> puntajes) {
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
		this.puntajes = puntajes;
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
	public int[] obtenerPuntaje() {
		int total = 0;
		int varios[] = {0, 0, 0, 0, 0, 0};
		for (Puntaje p : this.puntajes) {
			varios[p.getValor()] += 1;
			total += p.getValor();
		}
		if (total > 0) {
			varios[0] = total / this.puntajes.size();			
		}
		return varios;
	}
}