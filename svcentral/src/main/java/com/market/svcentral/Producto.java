package com.market.svcentral;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Producto {
	@Id
	private Integer numRef;
	
	@ManyToMany
	private List<Cat_Producto> categorias;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List <Comentario> comentarios;
	
	@ManyToOne
	@JoinColumn(name = "proveedor_nick")
	private Proveedor proveedor;
	
	private String nombre;
	
	private String descripcion;
	
	private float precio;
	
	
	private Integer stock;
	
	private String especificaciones;
	
	@ElementCollection
	private List<String> imagenes;
	
	private int cantidadCompras = 0; 
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "producto")
	List <Reclamo> reclamos;
	
	public Producto() {
		
	}
	
	// Constructor:
	public Producto(String nombre, String descripcion, float precio, Integer numRef, String especificaciones, Proveedor prov, int stock) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.numRef = numRef;
		this.stock = stock;
		this.especificaciones = especificaciones;
		this.categorias = new ArrayList<>();
		this.proveedor = prov;
		this.comentarios = new ArrayList<Comentario>();
		this.imagenes = new ArrayList<>();
		this.reclamos = new ArrayList<Reclamo>();
	}
	
	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}
	
	public void agregarComentario(Comentario comentario) {
	    this.comentarios.add(comentario);
	}

	public int getCantidadComprada() {
		return this.cantidadCompras;
	}
	
	public void setCantidadComprada(int cantidadComprada) {
		this.cantidadCompras = cantidadComprada;
	}

	public void agregarImagen(String img) {
		this.imagenes.add(img);
	}
	
	public List<String> getImagenes() {
		return this.imagenes;
	}
	
	public void agregarReclamo(Reclamo r) {
		this.reclamos.add(r);
	}
	
	public void agregarComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
	
	public void agregarRespuesta(int numeroComentario, Comentario respuesta) {
		Comentario c = this.comentarios.get(numeroComentario);
		c.agregarRespuesta(respuesta);
	}
	
	public List<Comentario> getComentarios() {
		return this.comentarios;
	}
	
	public Comentario getComentario(int num) {
		List<Comentario> listComentarios = this.comentarios;
		
		for (Comentario comentario: listComentarios) {
			if (comentario.getNumero() == num) {
				return comentario;
			}
		}
		
		return null;
	}
	
	
	public void agregarCategorias(Cat_Producto cat) {
		categorias.add(cat);
	}
	public void eliminarCategorias() {
		categorias.clear();
	}
	
	public List<Cat_Producto> getCategorias() {
		return this.categorias;
	}
	
	// Getters y Setters:
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Integer getNumRef() {
		return numRef;
	}

	public void setNumRef(Integer numRef) {
		this.numRef = numRef;
	}
	
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getEspecificaciones() {
		return this.especificaciones;
	}
	
	public String[] categoriasProducto() {
    	int contador = 0;
    	String[] arrString = new String[contador];
    		
    	for (Categoria entry: this.categorias) {
    		
    		Categoria cat = entry;
    		Cat_Producto cProducto = (Cat_Producto) cat;
    		arrString[contador++] = cProducto.getNombre();
    		
    	
    		Cat_Padre cPadre = cProducto.getPadre();
    		
    		while (cPadre.getPadre() != null) {
    			arrString[contador++] = cPadre.getNombre();
    			
    			
    			
    			cPadre = cPadre.getPadre();
    		}
    	}
    	
    	return arrString;
    }
	
	public DtProducto crearDT() {
		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•";
		String catStr = "";
		if (this.categorias.isEmpty()) {
			catStr = "El producto no tiene categorias asignadas";
		}
		for (Cat_Producto cat : this.categorias) {	
			catStr = catStr + "<br>" + tab + cat.getNombre();
			Cat_Padre cPadre = cat.getPadre();
            while (cPadre != null) {
            	catStr = catStr + " -> " + cPadre.getNombre();
                cPadre = cPadre.getPadre();
            }
		}
		catStr = catStr + "</html>";
		return new DtProducto(this.getNombre(), this.getDescripcion(), this.getPrecio(), this.getNumRef(), this.getEspecificaciones(), this.getProveedor(), catStr, this.getImagenes(), this.getStock(), this.getComentarios(), this.getCantidadComprada(), this.getReclamos());
	}
}