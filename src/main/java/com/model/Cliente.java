package com.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.exceptions.ProductoException;

public class Cliente extends Usuario {
    private Map<Integer, OrdenDeCompra> listaCompras;
    private Map<Integer, Comentario> listaComentarios;
    
    // Constructor
    public Cliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contrasena) {
        super(nombre, nick, apellido, correo, fecha, "cliente", contrasena);
        this.listaCompras = new HashMap<>();
        this.listaComentarios = new HashMap<>();
    }
    // gets, sets
    public Map<Integer, OrdenDeCompra> getCompras() {
        return listaCompras;
    }
    
    public void agregarRespuesta(int numeroComentario, String nombreProducto, Comentario r) {
    	for(Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra o = entry.getValue();
    		
    		
    		
    		for(Map.Entry<Integer, Item> entry2 : o.getItems().entrySet()) {
        		Item it = entry2.getValue();
        		
        		if(it.getProducto().getNombre() == nombreProducto) {
        			Producto p = it.getProducto();
        			p.agregarRespuesta(numeroComentario, r);
        			return;
        		}
        	}
    		
    	}
    }
    
    
    
    public void agregarComentario(Comentario c, String nombreProducto) throws ProductoException {
    	for(Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra o = entry.getValue();
    		
    		
    		
    		for(Map.Entry<Integer, Item> entry2 : o.getItems().entrySet()) {
        		Item it = entry2.getValue();
        		
        		if(it.getProducto().getNombre() == nombreProducto) {
        			Producto p = it.getProducto();
        			p.agregarComentario(c);
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
    	
    	for(Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra o = entry.getValue();
    		
    		lista.add(o.crearDT());
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

