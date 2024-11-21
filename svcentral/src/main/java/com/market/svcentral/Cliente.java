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
	
	
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrdenDeCompra> listaCompras;
    
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
        this.listaCompras = new ArrayList<OrdenDeCompra>();
        this.listaComentarios = new HashMap<>();
        this.carrito = new Carrito();
        this.listaPuntajes = new HashMap<>();
    }
    // gets, sets
    public List<OrdenDeCompra> getCompras() {
        return listaCompras;
    }
    
    public OrdenDeCompra getCompra(int numero) {
    	return this.listaCompras.get(numero);
    }
    
    
    public OrdenDeCompra getOrden(int numero) {
        return this.listaCompras.get(numero);
    }

    
    public void agregarRespuesta(int numeroComentario, String nombreProducto, Comentario respuesta) {
    	for (OrdenDeCompra entry : listaCompras) {
    		OrdenDeCompra orden = entry;
    		
    		
    		
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
    	for (OrdenDeCompra entry : listaCompras) {
    		OrdenDeCompra orden = entry;
    		
    		
    		
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
    	for (OrdenDeCompra entry : listaCompras) {
    		lista.add(entry.crearDT());
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
        listaCompras.add(orden);
    }
    public OrdenDeCompra obtenerOrden(int num) {
        for(OrdenDeCompra op: this.listaCompras) {
        	if(op.getNumero() == num) {
        		return op;
        	}
        }
        
        return null;
    	
    }
    public void eliminarOrden(int num) {
        listaCompras.remove(num);
    }
    public boolean existeOrden(int num) {
      for(OrdenDeCompra o: this.listaCompras) {
    	  if(o.getNumero() == num) {
    		  return true;
    	  }
      }
      
      return false;
   }
    public int cantCompras() {
        return listaCompras.size();
    }
    
    Cliente mostrarPerfil() {
    	return this;
    }
    
    public List<OrdenDeCompra> traerCompras(){
    	return this.listaCompras;
    }
    
    public Boolean comproProducto(int numeroRef) {
    	for (OrdenDeCompra entry : listaCompras) {
    		OrdenDeCompra orden = entry;
    		
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
    
 
    
    
    public Carrito getCarrito() {
        return this.carrito;
    }

    public void setCarrito(Carrito c1) {
        this.carrito = c1;
    }
    
    public List<OrdenDeCompra> getOrdenes() {
    	return this.listaCompras;
    }

    
    public DTCliente crearDt() {
        return new DTCliente(this.getNombre(), this.getNick(), this.getApellido(), this.getCorreo(), this.getNacimiento(), this.getImagen(), this.getCompras());
    }
    
    public boolean haCompradoDelProveedor(Proveedor proveedor) {
    	for (OrdenDeCompra orden : listaCompras) {
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
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	
    	for (OrdenDeCompra entry : listaCompras) {
    		OrdenDeCompra orden = entry;
    		for (Map.Entry<Integer, Item> entry2 : orden.getItems().entrySet()) {
        		Item item = entry2.getValue();
        		if (item.getProducto().getNumRef() == numRef) {
        			try {
        				Producto producto = item.getProducto();
        				if(this.listaPuntajes.containsKey(numRef)) {
        		            Puntaje p = this.listaPuntajes.get(numRef);
        		            p.setvalor(valor);
        		            em.merge(p);
        				}else {
        					
        					em.getTransaction().begin();
        					
        		            Puntaje puntaje = new Puntaje(valor, numRef);
        		            producto.agregarPuntaje(puntaje);
        		            this.listaPuntajes.put(numRef, puntaje);
        		            
        		            em.persist(puntaje);
        		            em.merge(this);
        		            em.merge(producto);
        		            
        		            em.getTransaction().commit();
        				}
        				
        				em.close();
        		    	emf.close();
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

