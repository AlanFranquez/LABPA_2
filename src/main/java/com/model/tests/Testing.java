package com.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.exceptions.*;
import com.model.*;

class Testing {
	private ISistema s = Factory.getSistema();
    
	@Test
    public void testValidarCorreo() {
		s.validarCorreo("correo");
    }
	
	@Test
    public void testAgregarProveedor() {
		DTFecha f = new DTFecha(1, 1, 1111);
		try {
			s.agregarProveedor("nick1", "correo1", "nombre", "apellido", f, "compania", "link", "contra", "contra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarProveedor: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarProveedor("nick2", "correo2", "nombre", "apellido", f, "compania", "link", "contra", "otracontra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarProveedor: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarProveedor("nick1", "correo3", "nombre", "apellido", f, "compania", "link", "contra", "contra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarProveedor: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarProveedor("nick", "correo1", "nombre", "apellido", f, "compania", "link", "contra", "contra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarProveedor: " + e.getMessage() + "\n");
		}
    }
	
	@Test
    public void testAgregarCliente() {
		DTFecha f = new DTFecha(1, 1, 1111);
		try {
			s.agregarCliente("nombre", "nick2", "apellido", "correo2", f, "contra", "contra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarCliente: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarCliente("nombre", "nick3", "apellido", "correo3", f, "contra", "otra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarCliente: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarCliente("nombre", "nick2", "apellido", "correo3", f, "contra", "contra");
		} catch (UsuarioRepetidoException e) {
			System.out.print("testAgregarCliente: " + e.getMessage() + "\n");
		}
    }
	
	@Test
	public void testAgregarImagenUsuario() {
		s.agregarImagenUsuario("nick1", "imagen");
		System.out.print("testAgregarImagenUsuario: ");
		s.agregarImagenUsuario("nick3", "imagen");
	}
	
	@Test
	public void testGetUsuario1() {
		Usuario u = s.getUsuario("nick1");
		assertEquals("nick1", u.getNick());
	}
	@Test
	public void testGetUsuario2() {
		Usuario u = s.getUsuario("otro");
		assertEquals(null, u);
	}
	
	@Test
	public void testVerificarUnicidadProducto() {
		s.listarClientes();
	}
}
