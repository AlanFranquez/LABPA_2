package com.market.svcentral;
import java.io.File;
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
    
    public String getNickname() {
        return this.getNick();
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

    public void registrarProducto(String nombre, String descripcion, float precio, Integer numRef, String especificaciones, Proveedor prov, int stock, Categoria[] cats, File[] archivosImagenes) {
        // Verificar que los parámetros necesarios no sean nulos
        if (nombre == null || descripcion == null || prov == null || cats == null || archivosImagenes == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos.");
        }
        
        // Crear el nuevo producto
        Producto prod = new Producto(nombre, descripcion, precio, numRef, especificaciones, prov, stock);

        // Verificar que al menos una categoría se haya proporcionado
        if (cats.length == 0) {
            throw new IllegalArgumentException("Se debe proporcionar al menos una categoría.");
        }

        // Agregar categorías al producto
        for (Categoria categoria : cats) {
            if (categoria instanceof Cat_Producto) {
                prod.agregarCategorias((Cat_Producto) categoria);
            } else {
                // Opcional: puedes lanzar una excepción o registrar un error si la categoría no es del tipo esperado
                System.err.println("Categoría no válida: " + categoria);
            }
        }

        // Agregar el producto a la colección (asegúrate de que esto maneje también las imágenes)
        this.agregarProd(prod);

        // Opcional: si las imágenes necesitan ser manejadas aquí, puedes hacerlo antes de agregar el producto
        // Forzar el manejo de imágenes según sea necesario
        // for (File imagen : archivosImagenes) {
        //     prod.agregarImagen(imagen); // Suponiendo que tienes un método para manejar imágenes
        // }
    }

}
