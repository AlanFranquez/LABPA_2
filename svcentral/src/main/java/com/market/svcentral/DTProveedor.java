package com.market.svcentral;

import java.util.List;

public class DTProveedor {
    private List<Producto> listaProductos;  // Usamos List en lugar de Map
    private String compania, link, nombre, nick, apellido, correo;
    private DTFecha nacimiento;
    private String imagen;
    
    // Constructor
    public DTProveedor(String nombre, String nick, String apellido, String correo, DTFecha nacimiento, String imagen, String compania, String link, List<Producto> listaProductos) {
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

    // Gets & Sets
    public String getCompania() {
        return compania;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Producto> getProductos() {
        return listaProductos;  // Retorna la lista de productos
    }

    // Métodos para manejar productos (ahora funcionan con List<Producto>)
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

    // Métodos de acceso
    public String getNombre() {
        return this.nombre;
    }

    // Los métodos getListaProductos y setListaProductos deben trabajar con List<Producto>
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
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

    public DTProveedor mostrarPerfil() {
        return this;  // Retorna el perfil del proveedor
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
