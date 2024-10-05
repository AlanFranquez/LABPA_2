package com.model;

import javax.swing.ImageIcon;

public class Usuario {
    private String nombre, nick, apellido, correo, tipo, contrasena;
    private DTFecha nacimiento;
    private ImageIcon imagen;
    
    // Constructor:
    public Usuario(String nombre, String nick, String apellido, String correo, DTFecha nacimiento, String tipo, String contrasena) {
        this.nombre = nombre;
        this.nick = nick;
        this.apellido = apellido;
        this.correo = correo;
        this.nacimiento = nacimiento;
        this.tipo = tipo;
        this.contrasena = contrasena;
    }
    
    // gets, sets
    public String getNombre(){
        return nombre;
    }
    public String getNick(){
        return nick;
    }
    public String getApellido(){
        return apellido;
    }
    public String getCorreo(){
        return correo;
    }
    public String getTipo() {
    	return tipo;
    }
    public DTFecha getNacimiento(){
        return nacimiento;
    }
    public String getContrasena(){
        return contrasena;
    }
    public ImageIcon getImagen() {
        return imagen;
    }
    
    
    public void setNombre(String Nombre){
        this.nombre = Nombre;
    }
    public void setNick(String Nick){
        this.nick = Nick;
    }
    public void setApellido(String Apellido){
        this.apellido = Apellido;
    }
    public void setCorreo(String Correo){
        this.correo = Correo;
    }
    public void setNacimiento(DTFecha nacimiento){
        this.nacimiento = nacimiento;
    }
    public void setContrasena(String contra){
        this.contrasena = contra;
    }
    public void setImagen(ImageIcon imagen) { 
        this.imagen = imagen;
    }
}
