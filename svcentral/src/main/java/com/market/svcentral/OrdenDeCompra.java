package com.market.svcentral;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.*;

@Entity
public class OrdenDeCompra {
    @Id
    private int numero;
    private float precioTotal;
    private LocalDateTime fecha;
    private int contadorCompras = 5;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Map<Integer, Item> items;
    
    // Cambio: Eliminamos @OneToMany ya que DTEstado es embebido
    @ElementCollection
    private List<DTEstado> estados;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Comentario> comentarios; 
    
    @ManyToOne
    @JoinColumn(name = "proveedorNick")
    private Proveedor proveedor;

    public OrdenDeCompra() {
        this.items = new HashMap<>();
        this.comentarios = new ArrayList<>();
        this.estados = new ArrayList<>();
    }

    public OrdenDeCompra(Map<Integer, Item> itemsAdquiridos, float precioTotal, Proveedor proveedor) {
        Random random = new Random();
        this.numero = random.nextInt(1000); // o algún otro método para generar números de orden únicos
        this.precioTotal = precioTotal;
        this.fecha = LocalDateTime.now();
        this.items = itemsAdquiridos;
        this.comentarios = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.proveedor = proveedor;

        // Estado inicial
        DTEstado estadoComprada = new DTEstado("Comprada", "El cliente ha realizado la compra");
        this.estados.add(estadoComprada);
    }
    
    // Probando
    public OrdenDeCompra(Proveedor proveedor) {

    	Random random = new Random();
        this.numero = random.nextInt(1000);
        this.precioTotal = 0;
        this.fecha = LocalDateTime.now();
        this.items = new HashMap<>();
        this.comentarios = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.proveedor = proveedor;

        // Estado inicial
        DTEstado estadoComprada = new DTEstado("Comprada", "El cliente ha realizado la compra");
        this.estados.add(estadoComprada);
    }
    
    public void agregarItem(Producto p, int cant) {
    	Item nuevoItem = new Item(cant, p);
    	
    	items.put(p.getNumRef(), nuevoItem);
    	setPrecioTotal();
    }
    
    
    // Métodos de gestión de estado
    public void agregarEstado(String estado, String comentarios) {
        DTEstado nuevoEstado = new DTEstado(estado, comentarios);
        this.estados.add(nuevoEstado);
    }
    
    public void setEstado(DTEstado estado) {
    	this.estados.add(estado);
    }

    public String getEstado() {
        return estados.isEmpty() ? "Sin estado" : estados.get(estados.size() - 1).getEstado();
    }

    public List<DTEstado> getHistorialEstado() {
        return estados;
    }

    // Otros métodos y getters/setters
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
    
   

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

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

    private void setPrecioTotal() {
        float total = 0;
        if (!items.isEmpty()) {
            for (Item i : items.values()) {
                total += i.getSubTotal();
            }
        }
        this.precioTotal = total;
    }

    // Método para crear el DTO de la orden
    public DTOrdenDeCompra crearDT() {
        return new DTOrdenDeCompra(numero, getItems(), getPrecioTotal(), getHistorialEstado());
    }
}
