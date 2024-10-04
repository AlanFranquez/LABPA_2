package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Producto {
	private Map<String, Cat_Producto> categorias;
	private List <Comentario> comentarios;
	private Proveedor proveedor;
	
	private String nombre, descripcion;
	private float precio;
	private Integer numRef, stock;
	private String especificaciones;
	private List<File> imagenes;
	
	// Constructor:
	public Producto(String nombre, String descripcion, float precio, Integer numRef, String especificaciones, Proveedor prov, int stock) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.numRef = numRef;
		this.stock = stock;
		this.especificaciones = especificaciones;
		this.categorias = new HashMap<>();
		this.proveedor = prov;
		this.comentarios = new ArrayList<>();
		this.imagenes = new ArrayList<>();
	}

	// Hacer una lista de imágenes en vez de una única
	public void agregarImagen(File img) {
		this.imagenes.add(img);
	}
	
	public List<File> getImagenes() {
		return this.imagenes;
	}
	
	public void agregarComentario(Comentario c) {
		this.comentarios.add(c);
	}
	
	public void agregarRespuesta(int numeroComentario, Comentario r) {
		Comentario c = this.comentarios.get(numeroComentario);
		c.agregarRespuesta(r);
	}
	
	public List<Comentario> getComentarios() {
		return this.comentarios;
	}
	
	
	public void agregarNuevasImagenes(List<File> imagenes) {
		this.imagenes = null;
		this.imagenes = imagenes;
	}
	
	public void agregarCategorias(Cat_Producto cat) {
		categorias.put(cat.getNombre(), cat);
	}
	public void eliminarCategorias() {
		categorias.clear();
	}
	
	public Map<String, Cat_Producto> getCategorias() {
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

	public void setProveedor(Proveedor prov) {
		this.proveedor = prov;
	}

	public String getEspecificaciones() {
		return this.especificaciones;
	}
	
	public String[] categoriasProducto() {
    	int contador = 0;
    	String[] arrString = new String[contador];
    		
    	for(Entry<String, Cat_Producto> entry : this.categorias.entrySet()) {
    		
    		Categoria c = entry.getValue();
    		Cat_Producto cProducto = (Cat_Producto) c;
    		arrString[contador++] = cProducto.getNombre();
    		
    	
    		Cat_Padre cPadre = cProducto.getPadre();
    		
    		while(cPadre.traerPadre() != null) {
    			arrString[contador++] = cPadre.getNombre();
    			
    			
    			
    			cPadre = cPadre.traerPadre();
    		}
    	}
    	
    	return arrString;
    }
	
	public DtProducto crearDT() {
		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•";
		String catStr = "";
		if(this.categorias.isEmpty()) {
			catStr = "El producto no tiene categorias asignadas";
		}
		for (Cat_Producto cat : this.categorias.values()) {	
			catStr = catStr + "<br>" + tab + cat.getNombre();
			Cat_Padre cPadre = cat.getPadre();
            while (cPadre != null) {
            	catStr = catStr + " -> " + cPadre.getNombre();
                cPadre = cPadre.traerPadre();
            }
		}
		catStr = catStr + "</html>";
		return new DtProducto(this.getNombre(), this.getDescripcion(), this.getPrecio(), this.getNumRef(), this.getEspecificaciones(), this.getProveedor(), catStr, this.getImagenes(), this.getStock(), this.getComentarios());
	}
}