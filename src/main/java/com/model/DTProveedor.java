package com.model;

import java.util.Map;




public class DTProveedor {
	private Map<Integer, Producto> listaProductos;  
    private String compania, link, nombre, nick, apellido, correo;
    private DTFecha nacimiento;
    private String imagen;
    // Constructor:
    public DTProveedor(String nombre, String nick, String apellido, String correo, DTFecha nacimiento, String imagen, String compania, String link, Map<Integer, Producto> listaProductos) {
    	   this.nombre = nombre;
           this.nick = nick;
           this.apellido = apellido;
           this.correo = correo;
           this.nacimiento = nacimiento;
           this.imagen = imagen;
           this.compania = compania;
           this.link = link;
           this.listaProductos = listaProductos;
    }
    // Gets sets
    public String getCompania() {
        return compania;
    }

 

    public String getLink() {
        return link;
    }

   
    public Map<Integer, Producto> getProductos() {
        return listaProductos;//??????'
    }
    public void agregarProd(int id, Producto prod) {
        listaProductos.put(id, prod);
    }
    public Producto obtenerProd(int id) {
        return listaProductos.get(id);
    }
    public boolean existeProd(int id) {
        return listaProductos.containsKey(id);
    }
    public int cantProd() {
        return listaProductos.size();
    }
	public String getNombre() {
		return this.nombre;
	}
	public Map<Integer, Producto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(Map<Integer, Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	
	 public String getNacimientoFormateado() {
	    	return this.nacimiento.getDia() + " / " + this.nacimiento.getMes() + " / " + this.nacimiento.getAnio();
	    }
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public DTFecha getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(DTFecha nacimiento) {
		this.nacimiento = nacimiento;
	}
	
	DTProveedor mostrarPerfil() {
		return this;
    	
    }
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
