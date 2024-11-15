package com.market.svcentral;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;

    // Constructor por defecto (obligatorio para serialización)
    public Persona() {
    }

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
