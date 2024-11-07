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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class OrdenDeCompra {
    @Id
	private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Map<Integer, Item> items;
    
    @Transient
    private List<DTEstado> estados;
    @Transient
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
        this.estados.add(new DTEstado("En preparación", "PREPARANDO PAQUETE"));
        
    }

    public OrdenDeCompra(Map<Integer, Item> itemsAdquiridos, float precioTotal, Proveedor proveedor) {
        Random random = new Random();
        this.items = itemsAdquiridos;
        this.numero = random.nextInt(1000);
        this.precioTotal = precioTotal;
        this.fecha = LocalDateTime.now();
        this.estados = new ArrayList<>();
        this.estados.add(new DTEstado("En preparación", "PREPARANDO PAQUETE"));
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
