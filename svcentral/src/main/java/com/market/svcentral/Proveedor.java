package com.market.svcentral;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("proveedor")
public class Proveedor extends Usuario {
    
    // Cambiar Map a List para la relación OneToMany
    @OneToMany(mappedBy = "proveedor")
    private List<Producto> listaProductos;  // Usamos List en lugar de Map
    
    private String compania;
    
    private String link;
    
    public Proveedor() {
        // Inicializamos listaProductos como un ArrayList
        this.listaProductos = new ArrayList<>();
    }
    
    // Constructor:
    public Proveedor(String nom, String nick, String ape, String correo, DTFecha nacimiento, String comp, String link, String contrasena) {
        super(nom, nick, ape, correo, nacimiento, "proveedor", contrasena);
        this.compania = comp;
        this.link = link;
        this.listaProductos = new ArrayList<>();
    }
    
    // Métodos getter y setter para compania y link
    public String getCompania() {
        return compania;
    }

    public void setCompania(String comp) {
        this.compania = comp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String lin) {
        this.link = lin;
    }
    
    // Getter para obtener los productos
    public List<Producto> getProductos() {
        return this.listaProductos;
    }

    // Métodos para manejar productos
    public void agregarProd(Producto prod) {
        listaProductos.add(prod);  // Agregar un producto a la lista
    }

    public Producto obtenerProd(int numRef) {
        // Buscar el producto en la lista por numRef
        return listaProductos.stream()
                             .filter(p -> p.getNumRef().equals(numRef))
                             .findFirst()
                             .orElse(null);  // Devuelve null si no lo encuentra
    }

    public void eliminarProd(int numRef) {
        // Buscar y eliminar el producto en la lista por numRef
        Producto prod = obtenerProd(numRef);
        if (prod != null) {
            listaProductos.remove(prod);
        }
    }

    public boolean existeProd(int numRef) {
        // Verificar si un producto con numRef existe en la lista
        return listaProductos.stream().anyMatch(p -> p.getNumRef().equals(numRef));
    }

    public int cantProd() {
        // Devuelve el número de productos en la lista
        return listaProductos.size();
    }
    

    // Método para mostrar perfil del proveedor
    Proveedor mostrarPerfil() {
        return this;
    }
    
    // Método para crear DTProveedor
    public DTProveedor crearDt() {
        return new DTProveedor(this.getNombre(), getNick(), getApellido(), getCorreo(), getNacimiento(), getImagen(), getCompania(), getLink(), this.getProductos());
    }

    // Método para registrar un producto
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

        // Agregar el producto a la lista
        this.agregarProd(prod);

        // Opcional: Si las imágenes necesitan ser gestionadas, puedes hacerlo aquí
        // Forzar el manejo de imágenes según sea necesario
        // for (File imagen : archivosImagenes) {
        //     prod.agregarImagen(imagen); // Suponiendo que tienes un método para manejar imágenes
        // }
    }
}
