package com.exceptions;

@SuppressWarnings("serial")
public class UsuarioRepetidoException extends Exception {

	public UsuarioRepetidoException(String mensaje) {
        super(mensaje);
    }
}
