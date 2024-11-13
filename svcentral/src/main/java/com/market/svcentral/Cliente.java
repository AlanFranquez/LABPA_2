package com.market.svcentral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.market.svcentral.exceptions.ProductoException;

public class Cliente extends Usuario {
    private Map<Integer, OrdenDeCompra> listaCompras;
    private Map<Integer, Comentario> listaComentarios;
    private Carrito carrito;
    
    // Booleans para controlar notificaciones
    private boolean recibirNotificaciones;
    private boolean recibirNotificacionesNuevoProducto;
    private boolean recibirNotificacionesComentario;
    
    // Tokens para cada tipo de notificación
    private String tokenDesactivacion;
    private String tokenNuevoProducto;
    private String tokenComentario;

    
    // Constructor
    public Cliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contrasena) {
        super(nombre, nick, apellido, correo, fecha, "cliente", contrasena);
        this.listaCompras = new HashMap<>();
        this.listaComentarios = new HashMap<>();
        this.carrito = new Carrito();
        
        // Inicialización de los booleanos de notificación a false
        this.recibirNotificaciones = false;
        this.recibirNotificacionesNuevoProducto = false;
        this.recibirNotificacionesComentario = false;
        
        // Generación de tokens únicos para cada tipo de notificación
        this.tokenDesactivacion = generarTokenUnico();
        this.tokenNuevoProducto = generarTokenUnico();
        this.tokenComentario = generarTokenUnico();
    }
    
    // Métodos para obtener y establecer valores de notificación y tokens
    public boolean isRecibirNotificaciones() {
        return recibirNotificaciones;
    }
    
    public void setRecibirNotificaciones(boolean recibirNotificaciones) {
        this.recibirNotificaciones = recibirNotificaciones;
    }

    public boolean isRecibirNotificacionesNuevoProducto() {
        return recibirNotificacionesNuevoProducto;
    }

    public void setRecibirNotificacionesNuevoProducto(boolean recibirNotificacionesNuevoProducto) {
        this.recibirNotificacionesNuevoProducto = recibirNotificacionesNuevoProducto;
    }

    public boolean isRecibirNotificacionesComentario() {
        return recibirNotificacionesComentario;
    }

    public void setRecibirNotificacionesComentario(boolean recibirNotificacionesComentario) {
        this.recibirNotificacionesComentario = recibirNotificacionesComentario;
    }

    public String getTokenDesactivacion() {
        return tokenDesactivacion;
    }

    public void setTokenDesactivacion(String tokenDesactivacion) {
        this.tokenDesactivacion = tokenDesactivacion;
    }

    public String getTokenNuevoProducto() {
        return tokenNuevoProducto;
    }

    public void setTokenNuevoProducto(String tokenNuevoProducto) {
        this.tokenNuevoProducto = tokenNuevoProducto;
    }

    public String getTokenComentario() {
        return tokenComentario;
    }

    public void setTokenComentario(String tokenComentario) {
        this.tokenComentario = tokenComentario;
    }

    // Método para generar un token único
    private String generarTokenUnico() {
        return UUID.randomUUID().toString();
    }
    
    // Otros métodos existentes de la clase Cliente
    
    public Map<Integer, OrdenDeCompra> getCompras() {
        return listaCompras;
    }
    
    public OrdenDeCompra getCompra(int numero) {
        return this.listaCompras.get(numero);
    }
    
    public Carrito getCarrito() {
        return this.carrito;
    }
    
    public OrdenDeCompra getOrden(int numero) {
        return this.listaCompras.get(numero);
    }

    public void agregarRespuesta(int numeroComentario, String nombreProducto, Comentario respuesta) {
        for (Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
            OrdenDeCompra orden = entry.getValue();
            for (Map.Entry<Integer, Item> entry2 : orden.getItems().entrySet()) {
                Item item = entry2.getValue();
                if (item.getProducto().getNombre().equals(nombreProducto)) {
                    Producto producto = item.getProducto();
                    producto.agregarRespuesta(numeroComentario, respuesta);
                    return;
                }
            }
        }
    }

    public void agregarComentario(Comentario comentario, String nombreProducto) throws ProductoException {
        for (Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
            OrdenDeCompra orden = entry.getValue();
            for (Map.Entry<Integer, Item> entry2 : orden.getItems().entrySet()) {
                Item item = entry2.getValue();
                if (item.getProducto().getNombre().equals(nombreProducto)) {
                    Producto producto = item.getProducto();
                    producto.agregarComentario(comentario);
                    return;
                }
            }
        }
        throw new ProductoException("El cliente no compró el producto");
    }

    public OrdenDeCompra getCompraParticular(int numero) {
        return this.listaCompras.get(numero);
    }
    
    public List<DTOrdenDeCompra> mostrarCompras() {
        List<DTOrdenDeCompra> lista = new ArrayList<>();
        for (OrdenDeCompra orden : listaCompras.values()) {
            lista.add(orden.crearDT());
        }
        return lista;
    }
    
    public DTOrdenDeCompra mostrarCompras(int numero) {
        return this.listaCompras.get(numero).crearDT();
    }
    
    public Map<Integer, Comentario> getComentarios() {
        return listaComentarios;
    }

    public void agregarCompra(OrdenDeCompra orden) {
        listaCompras.put(orden.getNumero(), orden);
    }

    public OrdenDeCompra obtenerOrden(int num) {
        return listaCompras.get(num);
    }

    public void eliminarOrden(int num) {
        listaCompras.remove(num);
    }

    public boolean existeOrden(int num) {
        return listaCompras.containsKey(num);
    }

    public int cantCompras() {
        return listaCompras.size();
    }

    public Cliente mostrarPerfil() {
        return this;
    }

    public Boolean comproProducto(int numeroRef) {
        for (OrdenDeCompra orden : listaCompras.values()) {
            for (Item item : orden.getItems().values()) {
                if (item.getProducto().getNumRef() == numeroRef) {
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Integer> getAllOrdenes() {
        Set<Integer> res = new HashSet<>();
        for (OrdenDeCompra orden : listaCompras.values()) {
            res.add(orden.getNumero());
        }
        return res;
    }

    public List<OrdenDeCompra> getOrdenes() {
        return new ArrayList<>(listaCompras.values());
    }
    
    public DTCliente crearDt() {
        return new DTCliente(this.getNombre(), this.getNick(), this.getApellido(), this.getCorreo(), this.getNacimiento(), this.getImagen(), this.getCompras());
    }

    public boolean haCompradoDelProveedor(Proveedor proveedor) {
        for (OrdenDeCompra orden : this.listaCompras.values()) {
            if (orden.getProveedor().equals(proveedor)) {
                return true;
            }
        }
        return false;
    }




}
