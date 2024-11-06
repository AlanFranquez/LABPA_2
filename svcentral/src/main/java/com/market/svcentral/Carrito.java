package com.market.svcentral;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class Carrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany
	private List<Item> listItems;

    public Carrito() {
        listItems = new ArrayList<>();
    }
    
    public List<Item> getProductos() {
    	return this.listItems;
    }
    
    public int getId() {
    	return this.id;
    }

    
    public void agregarProducto(Item item) {
        if (item.getProducto().getStock() >= item.getCant()) {
            listItems.add(item);
        } else {
            System.out.println("No se puede agregar el producto. Stock insuficiente. ");
        }
    }
    
    public void vaciarCarrito() {
        listItems.clear();
    }
    
    public void eliminarProd(int numRef) {
    	 for (int i = 0; i < listItems.size(); i++) {
             if (listItems.get(i).getProducto().getNumRef() == numRef) {
                 listItems.remove(i);
                 return;
             }
         }
    }
    
    public Boolean existeProducto(int numRef) {
    	for(Item item : listItems) {
    		if(item.getProducto().getNumRef() == numRef) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public Item getItem(int numRef){
    	for (Item item : listItems) {
    		if (item.getProducto().getNumRef() == numRef) {
    			return item;
    		}
    	}
    	
    	return null;
    }

    public void verCarrito() {
        if (listItems.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            for (Item productoCarrito : listItems) {
                System.out.println(productoCarrito.getProducto().getNombre());
            }
        }
    }
    int numeroOrden = 1;
    public int generarNumeroOrden() {
    	return numeroOrden++;
	 }

    
}

