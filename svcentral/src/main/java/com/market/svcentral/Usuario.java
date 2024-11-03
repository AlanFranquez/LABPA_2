package com.market.svcentral;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

// Clase usuario
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Usuario {
    // Atributos
    @Id
    private String nick; // Cambiado a String y será el identificador único

    private String nombre;
    private String apellido;
    private String correo;
    private String tipo;
    private String contrasena;
    @Transient
    private DTFecha nacimiento;
    
    private String imagen;

    public Usuario() {
    }

    // Constructor
    public Usuario(final String nombre, final String nick, final String apellido, final String correo, final DTFecha nacimiento, final String tipo, final String contrasena) {
        this.nombre = nombre;
        this.nick = nick;
        this.apellido = apellido;
        this.correo = correo;
        this.nacimiento = nacimiento;
        this.tipo = tipo;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getNick() {
        return nick;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTipo() {
        return tipo;
    }

    public DTFecha getNacimiento() {
        return nacimiento;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getImagen() {
        return imagen;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setNick(final String nick) {
        this.nick = nick;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    public void setNacimiento(final DTFecha nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setContrasena(final String contra) {
        this.contrasena = contra;
    }

    public void setImagen(final String imagen) {
        this.imagen = imagen;
    }
}
