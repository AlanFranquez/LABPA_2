package com.market.svcentral;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class usuarioRandom {

    @Id
    private String nombre;
    private int edad;

    // Default constructor (no-argument constructor)
    public usuarioRandom() {
        // This constructor is required by JPA
    }

    // Parameterized constructor
    public usuarioRandom(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getEdad() {
        return this.edad;
    }
}
