package com.market.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.Endpoint;

import com.market.svcentral.Carrito;
import com.market.svcentral.Cliente;
import com.market.svcentral.DtProducto;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;

@WebService
public class Publicador {
    private Endpoint endpoint = null;
    private ISistema s;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    EntityManager em = emf.createEntityManager();

    // Constructor para inicializar el sistema
    public Publicador() {
        s = Factory.getSistema();
    }

    @WebMethod(exclude = true)
    public void publicar(String url) {
        if (endpoint == null || !endpoint.isPublished()) {
            endpoint = Endpoint.publish(url, this);
            System.out.println("Servicio publicado en: " + url);
        }
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return this.endpoint;
    }
    
    // ALAN

    @WebMethod
    public Producto obtenerProducto(int numRef) {
        return em.find(Producto.class, numRef);
    }
    
    @WebMethod
    public List<Producto> listarProductos() {
    	return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    	
    }
    
   
    
    @WebMethod
    public String verificarClienteYCrearCarrito(String nickUsuario) {
        EntityManager em = emf.createEntityManager();
        String resultado;
        try {
            em.getTransaction().begin();

            // Obtener usuario de la base de datos
            Usuario usuarioLogueado = em.find(Usuario.class, nickUsuario);
            if (usuarioLogueado instanceof Cliente) {
                Cliente clienteLogueado = (Cliente) usuarioLogueado;
                System.out.println("Cliente logueado: " + clienteLogueado.getNick());

                // Verificar si el cliente tiene carrito
                if (clienteLogueado.getCarrito() == null) {
                    System.out.println("Carrito no encontrado para el cliente, creando nuevo carrito.");
                    Carrito nuevoCarrito = new Carrito();
                    clienteLogueado.setCarrito(nuevoCarrito);
                    em.persist(nuevoCarrito);
                    resultado = "Nuevo carrito creado para el cliente: " + clienteLogueado.getNick();
                } else {
                    resultado = "Cliente ya tiene un carrito con ID: " + clienteLogueado.getCarrito().getId();
                }

                em.getTransaction().commit();
            } else {
                resultado = "El usuario no es un cliente.";
            }
        } catch (Exception e) {
            resultado = "Error al verificar el cliente: " + e.getMessage();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return resultado;
    }

    @WebMethod
    public Carrito obtenerCarritoDeCliente(String nickUsuario) {
        EntityManager em = emf.createEntityManager();
        Carrito carrito = null;
        try {
            Usuario usuarioLogueado = em.find(Usuario.class, nickUsuario);
            if (usuarioLogueado instanceof Cliente) {
                Cliente clienteLogueado = (Cliente) usuarioLogueado;
                carrito = clienteLogueado.getCarrito();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return carrito;
    }
    
    @WebMethod
    public Boolean comprobarCliente(String nick) {
    	return s.getUsuario(nick).getTipo() == "cliente";
    }
    
    @WebMethod
    public Usuario obtenerUsuario(String nick) {
    	return em.find(Usuario.class, nick);
    }
    
    @WebMethod
    public Cliente obtenerCliente(String nick) {
    	return (Cliente) em.find(Cliente.class, nick);
    }
    
    @WebMethod
    public void setCarritoCliente(String nickName, Carrito nuevoCarrito) {
    	EntityManager em = emf.createEntityManager();
    	Cliente cl = this.obtenerCliente(nickName);
    	
    	cl.setCarrito(nuevoCarrito);
    	em.persist(nuevoCarrito);
    	
    	em.close();
    }
    
    @WebMethod
    public List<String> obtenerImagenesProducto(Producto p) {
    	return this.obtenerProducto(p.getNumRef()).getImagenes();
    }
    

    @WebMethod
    public String obtenerPrimeraImagenProducto(Producto p) {
    	return this.obtenerProducto(p.getNumRef()).getImagenes().get(0);
    }
    
    
    
    
    // CARLITOS
    
    
    // FABRICIO
    
    
    //RENZO
    

    public String saludar() {
        return "Hola Mundo";
    }
}
