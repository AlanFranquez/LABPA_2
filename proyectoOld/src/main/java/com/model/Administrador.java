package com.model;

public class Administrador extends Usuario {
	public Administrador(String nom, String nick, String ape, String correo, DTFecha nacimiento, String contrasena) {
        super(nom, nick, ape, correo, nacimiento, "Admin", contrasena);
    }
}
