package com.market.svcentral;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

@Entity
public class OrdenDeCompra {
    @Id
	private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Map<Integer, Item> items;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<DTEstado> estados;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Comentario> comentarios; 
    private Proveedor proveedor;

    public OrdenDeCompra(){
    	
    }
    
    public OrdenDeCompra(int numero) {
        this.fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = 0;
        this.items = new HashMap<>();
        this.comentarios = new ArrayList<>(); 
        this.estados = new ArrayList<>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DTEstado> query = em.createQuery(
                "SELECT e FROM DTEstado e WHERE e.estado = :estado", DTEstado.class);
            query.setParameter("estado", "Comprada");

            DTEstado estadoComprada = query.getSingleResult();
            this.estados.add(estadoComprada);
        } catch (NoResultException e) {
            // Manejar el caso cuando no se encuentra el estado
            System.out.println("No se encontró el estado 'Comprada'");
        } finally {
            em.close();
        }

        
    }

    public OrdenDeCompra(Map<Integer, Item> itemsAdquiridos, float precioTotal, Proveedor proveedor) {
        Random random = new Random();
        this.items = itemsAdquiridos;
        this.numero = random.nextInt(1000);
        this.precioTotal = precioTotal;
        this.fecha = LocalDateTime.now();
        this.estados = new ArrayList<>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DTEstado> query = em.createQuery(
                "SELECT e FROM DTEstado e WHERE e.estado = :estado", DTEstado.class);
            query.setParameter("estado", "Comprada");

            DTEstado estadoComprada = query.getSingleResult();
            this.estados.add(estadoComprada);
        } catch (NoResultException e) {
            // Manejar el caso cuando no se encuentra el estado
            System.out.println("No se encontró el estado 'Comprada'");
        } finally {
            em.close();
        }
        
        this.proveedor = proveedor;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void agregarComentario(String comentarioTexto, Cliente autor) {
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede ser null");
        }
        Comentario nuevoComentario = new Comentario(numero, comentarioTexto, autor, fecha);
        comentarios.add(nuevoComentario);
    }



    // Métodos para gestionar los estados
    public String getEstado() {
        //return estados.getLast().getEstado(); // Devuelve el último estado
        return estados.get(estados.size() - 1).getEstado(); // Accede al último estado por índice

    }

    public void setEstado(String nuevoEstado, String comentarios) {
        estados.add(new DTEstado(nuevoEstado, comentarios));
    }
    
    public void setEstado(DTEstado estadoComprada) {
		this.estados.add(estadoComprada);
	}

   
    public List<DTEstado> getHistorialEstado() {
        return estados;
    }
    

    // Getters y Setters:
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    private void setPrecioTotal() {
        float total = 0;
        if (items.isEmpty()) {
            this.precioTotal = 0;
        } else {
            Collection<Item> col = items.values();
            for (Item i : col) {
                total += i.getSubTotal();
            }
            this.precioTotal = total;
        }
    }

    // Métodos para manejar los items
    public void addItem(Producto producto, int cant) {
        if (items.containsKey(producto.getNumRef())) {
            Item item = items.get(producto.getNumRef());
            item.setCant(item.getCant() + cant);
        } else {
            Item item = new Item(cant, producto);
            items.put(producto.getNumRef(), item);            
        }
        setPrecioTotal();
    }

    public void removeItem(Integer codigo) {
        items.remove(codigo);
        setPrecioTotal();
    }

    public DTOrdenDeCompra crearDT() {
        return new DTOrdenDeCompra(numero, getItems(), getPrecioTotal(), getHistorialEstado());
    }

	
   
    

}
