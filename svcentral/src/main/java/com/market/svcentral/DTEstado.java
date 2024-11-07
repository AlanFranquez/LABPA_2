package com.market.svcentral;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;


@Entity
@Table(name = "dtestado")  
public class DTEstado {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String estado;
    private LocalDateTime fecha;
    private String comentarios;

    public DTEstado(String estado, String comentarios) {
        this.estado = estado;
        this.fecha = LocalDateTime.now();
        this.comentarios = comentarios;
        this.comentarios = (comentarios != null) ? comentarios : "Sin comentarios";
    }
    
    public DTEstado() {
        // Constructor por defecto
    }

    // Getters y setters

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

  

    public String getFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return fecha.format(formatter);
    }


    public String getComentarios() {
        return comentarios;
    }


}
