package com.market.svcentral;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;


@Entity
public class DTOrdenDeCompra {
    @Id
	private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Map<Integer, Item> items;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<DTEstado> estados;

    public DTOrdenDeCompra(int numero, Map<Integer, Item> items, float precioTotal, String estado) {
        this.fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = precioTotal;
        this.items = items;
        this.estados = new ArrayList<DTEstado>(); 
        
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
    
    public DTOrdenDeCompra(int numero, Map<Integer, Item> items, float precioTotal, List<DTEstado> estadosLista) {
        this.fecha = LocalDateTime.now();
        this.numero = numero;
        this.precioTotal = precioTotal;
        this.items = items;
        this.estados = estadosLista;
    }

    public List<DTItem> listarItems() {
        List<DTItem> listarItems = new ArrayList<>();

        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item item = entry.getValue();
            listarItems.add(item.crearDT());
        }

        return listarItems;
    }

    // Getters y Setters

    public String getEstado() {
        if (estados != null && !estados.isEmpty()) {
            return estados.get(estados.size() - 1).getEstado(); // Accede al último elemento
        }
        return "Sin estado"; // Retorno por defecto en caso de que la lista esté vacía
    }

    public List<DTEstado> getHistorialEstado() {
        return this.estados;
    }

    public int getNumero() {
        return numero;
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

    public String getFechaString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formatter);
    }

    @Override
    public String toString() {
        String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String itemsStr = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Item item : this.items.values()) {
            itemsStr += tab + tab + item.getProducto().getNombre() + ": " + item.getCant() + "<br />";
        }

        return "<html>Orden número = " + numero + "<br />" + tab + "precioTotal = " + precioTotal + "<br />" + tab + "fecha = "
                + fecha.format(formatter) + "<br />" + tab + "items:<br />" + itemsStr + "</html>";
    }
}
