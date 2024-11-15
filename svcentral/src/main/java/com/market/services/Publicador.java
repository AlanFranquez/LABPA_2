package com.market.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.Endpoint;

import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;

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

    public Producto obtenerProducto(int numRef) {
        return em.find(Producto.class, numRef);
    }
    
    public List<Producto> listarProductos() {
    	return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    	
    }
    
    
    // CARLITOS
    
    
    // FABRICIO
    
    
    //RENZO
    

    public String saludar() {
        return "Hola Mundo";
    }
}
