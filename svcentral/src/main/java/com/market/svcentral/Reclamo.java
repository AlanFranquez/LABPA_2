package com.market.svcentral;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessorType;


@Entity
@XmlAccessorType
public class Reclamo implements Serializable {
	@Id
	private int id;
	
	
	private LocalDateTime fecha;
	
	private String texto;
	
	@ManyToOne
	@JoinColumn(name = "producto_numref")
	Producto producto;
	
	@ManyToOne
	@JoinColumn(name = "proveedor_nick")
	Proveedor proveedor;
	
	@ManyToOne(cascade = CascadeType.MERGE) // Agregar cascade PERSIST
    @JoinColumn(name = "cliente_id")
	Cliente autor;
	
	public Reclamo() {
		
	}

	public Reclamo(String texto, LocalDateTime fecha, Producto producto, Proveedor proveedor, Cliente autor) {
		Random nuevo = new Random();
		this.id = nuevo.nextInt(5000);
		
		this.texto = texto;
		this.fecha = fecha;
		this.producto = producto;
		this.proveedor = proveedor;
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}
	
	public int getId() {
		return this.id;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public LocalDateTime getFecha() {
        return fecha;
    }
	
	public String getFechaFormat() {
	
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = this.fecha.format(formatter);
        return formattedDateTime;
	}
	
	public Producto getProducto() {
        return producto;
    }
	
	public Proveedor getProveedor() {
        return proveedor;
    }
	
	public Cliente getAutor() {
        return autor;
    }
	
	public DTReclamo crearDT() {
		return new DTReclamo(this.texto, this.fecha, this.producto, this.proveedor, this.autor);
	}
}