package com.market.svcentral;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.market.svcentral.exceptions.ProductoException;

public class Cliente extends Usuario {
    private Map<Integer, OrdenDeCompra> listaCompras;
    private Map<Integer, Comentario> listaComentarios;
    private Carrito carrito;
    
    // Constructor
    public Cliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contrasena) {
        super(nombre, nick, apellido, correo, fecha, "cliente", contrasena);
        this.listaCompras = new HashMap<>();
        this.listaComentarios = new HashMap<>();
        this.carrito = new Carrito();
    }
    // gets, sets
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
        		
        		if (item.getProducto().getNombre() == nombreProducto) {
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
        		
        		if (item.getProducto().getNombre() == nombreProducto) {
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
    	List<DTOrdenDeCompra> lista = new ArrayList<DTOrdenDeCompra>();
    	
    	for (Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra orden = entry.getValue();
    		
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
    
    Cliente mostrarPerfil() {
    	return this;
    }
    
    public Boolean comproProducto(int numeroRef) {
    	for (Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra orden = entry.getValue();
    		
    		Map<Integer, Item> items = orden.getItems();
    		
    		
    		for (Map.Entry<Integer, Item> entry2 : items.entrySet()) {
    			Item item = entry2.getValue();
    			
    			
    			if (item.getProducto().getNumRef() == numeroRef) {
    				return true;
    			}
    		}
        }
    	
    	return false;
    }
    
    
   
    // Mas que un set de integers creo que debería de ser un arreglo de dtOrdenCompra
    public Set<Integer> getAllOrdenes() {
        Set<Integer> res = new HashSet<>();
        for (OrdenDeCompra ordenActual : listaCompras.values()) {
            res.add(ordenActual.getNumero());
        }
        return res;
    }
    
    public DTCliente crearDt() {
        return new DTCliente(this.getNombre(), this.getNick(), this.getApellido(), this.getCorreo(), this.getNacimiento(), this.getImagen(), this.getCompras());
    }
}

