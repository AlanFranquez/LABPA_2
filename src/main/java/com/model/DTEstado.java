package com.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DTEstado {
    private String nombre;
    private LocalDateTime fecha;
    private String comentario;
    
    // Constructor
    public DTEstado(String nombre) {
        this.nombre = nombre;
        this.fecha = LocalDateTime.now();
        this.comentario = "LALALA1";
    }
    
    public DTEstado() {
        this.nombre = "En preparacion";
        this.fecha = LocalDateTime.now();
        this.comentario = "Lalala2";
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return this.fecha.toString();
    }
    
    public String getFechaDetalle() {
        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return fecha.format(formatter);
    }
    
    public String getCom() {
        return comentario;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    

    

    // Método toString opcional para representación
    @Override
    public String toString() {
        return "DTEstado{nombre='" + nombre + "', fecha='" + fecha + "', comentario='\"" + comentario + "\"'}";
    }
}
