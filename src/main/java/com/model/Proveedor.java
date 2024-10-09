package com.model;
import java.util.HashMap;
import java.util.Map;

public class Proveedor extends Usuario {
    private Map<Integer, Producto> listaProductos;  
    private String compania, link;
    // Constructor:
    public Proveedor(String nom, String nick, String ape, String correo, DTFecha nacimiento, String comp, String link, String contrasena) {
        super(nom, nick, ape, correo, nacimiento, "proveedor", contrasena);
        this.compania = comp;
        this.link = link;
        this.listaProductos = new HashMap<>();
    }
    // Gets sets
    public String getCompania() {
        return compania;
    }

    public void setCompania(String comp) {
        this.compania = comp;
    }

    
    public Map<Integer, Producto> getProductos() {
    	return this.listaProductos;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String lin) {
        this.link = lin;
    }

    public void agregarProd(Producto prod) {
        listaProductos.put(prod.getNumRef(), prod);
    }
    public Producto obtenerProd(int numRef) {
        return listaProductos.get(numRef);
    }
    public void eliminarProd(int numRef) {
        listaProductos.remove(numRef);
    }
    public boolean existeProd(int numRef) {
        return listaProductos.containsKey(numRef);
    }
    public int cantProd() {
        return listaProductos.size();
    }
    
    Proveedor mostrarPerfil() {
		return this;
    	
    }
    
  
    
    public DTProveedor crearDt() {
        return new DTProveedor(this.getNombre(), getNick(), getApellido(), getCorreo(), getNacimiento(), getImagen(), getCompania(), getLink(), this.getProductos());
    }

}
