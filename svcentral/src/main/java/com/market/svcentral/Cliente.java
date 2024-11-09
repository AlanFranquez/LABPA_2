package com.market.svcentral;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Transient;

import com.market.svcentral.exceptions.ProductoException;
import com.market.svcentral.exceptions.PuntajeInvalidoException;
@Entity
@DiscriminatorValue("cliente")
public class Cliente extends Usuario {
	
	
	@OneToMany
    private Map<Integer, OrdenDeCompra> listaCompras;
    
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_nick")
    private Map<Integer, Puntaje> listaPuntajes;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.MERGE)
    private Map<Integer, Comentario> listaComentarios;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Carrito carrito;
    
    public Cliente() {
        
    }
    
    // Constructor
    public Cliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contrasena) {
        super(nombre, nick, apellido, correo, fecha, "cliente", contrasena);
        this.listaCompras = new HashMap<>();
        this.listaComentarios = new HashMap<>();
        this.carrito = new Carrito();
        this.listaPuntajes = new HashMap<>();
    }
    // gets, sets
    public Map<Integer, OrdenDeCompra> getCompras() {
        return listaCompras;
    }
    
    public OrdenDeCompra getCompra(int numero) {
    	return this.listaCompras.get(numero);
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
    	System.out.println(this.cantCompras());
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
    
    public Carrito getCarrito() {
        return this.carrito;
    }

    public void setCarrito(Carrito c1) {
        this.carrito = c1;
    }
    public List<OrdenDeCompra> getOrdenes() {
        return new ArrayList<>(listaCompras.values());
    }
    
    public DTCliente crearDt() {
        return new DTCliente(this.getNombre(), this.getNick(), this.getApellido(), this.getCorreo(), this.getNacimiento(), this.getImagen(), this.getCompras());
    }
	/*public boolean haCompradoDelProveedor(Proveedor proveedor) {
		// TODO Auto-generated method stub
		return false;
	}*/
    
    
    public boolean haCompradoDelProveedor(Proveedor proveedor) {
        for (OrdenDeCompra orden : this.listaCompras.values()) {
            // Imprimir el proveedor de la orden para depuración
            System.out.println("Comparando con proveedor: " + orden.getProveedor().getNombre());
            
            // Usar equals para la comparación
            if (orden.getProveedor().equals(proveedor)) {
                return true; // Se encontró al menos una compra del proveedor
            }
        }
        return false; // No se encontraron compras del proveedor
    }

    public Map<Integer, Puntaje> getPuntajes() {
        return this.listaPuntajes;
    }
    
    public Puntaje getPuntaje(int numero) {
    	return this.listaPuntajes.get(numero);
    }
    
    public void agregarPuntaje(Integer valor, Integer numRef) throws ProductoException {
    	for (Map.Entry<Integer, OrdenDeCompra> entry : listaCompras.entrySet()) {
    		OrdenDeCompra orden = entry.getValue();
    		for (Map.Entry<Integer, Item> entry2 : orden.getItems().entrySet()) {
        		Item item = entry2.getValue();
        		if (item.getProducto().getNumRef() == numRef) {
        			try {
        				Producto producto = item.getProducto();
        				if(this.listaPuntajes.containsKey(numRef)) {
        		            Puntaje p = this.listaPuntajes.get(numRef);
        		            p.setvalor(valor);
        				}else {
        		            Puntaje puntaje = new Puntaje(valor, numRef);
        		            producto.agregarPuntaje(puntaje);
        		            this.listaPuntajes.put(numRef, puntaje);        					
        				}
        				return;        				
        			}catch (PuntajeInvalidoException e) {
        				System.out.println(e.getMessage());
        			}
        		}
        	}
    	}
    	throw new ProductoException("El cliente no compró el producto");
    }
	 
}

