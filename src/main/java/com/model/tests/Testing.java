package com.model.tests;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.exceptions.CategoriaException;
import com.exceptions.UsuarioRepetidoException;
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
    public void testVerificarUnicidadProductoExistente() {
        String categoria = "Electrónica";
        int numRef = 12345;
        String titulo = "Televisor 4K";

        // Agregar un producto para la verificación
        s.agregarProducto(titulo, numRef, "Descripción del producto", "Especificaciones", 999.99f, "proveedor1", 10);

        // Probar la unicidad
        boolean resultado = s.verificarUnicidadProducto(categoria, numRef, titulo);
        assertFalse("El producto debería ser único pero no lo es.", resultado);
    }

    @Test
    public void testVerificarUnicidadProductoNoExistente() {
        String categoria = "Electrónica";
        int numRef = 54321;
        String titulo = "Laptop Gaming";

        boolean resultado = s.verificarUnicidadProducto(categoria, numRef, titulo);
        assertTrue("El producto debería ser único.", resultado);
    }

    @Test
    public void testAgregarProductoCorrecto() {
        String titulo = "Smartphone";
        int numRef = 67890;
        String descripcion = "Un smartphone de última generación";
        String especificaciones = "Especificaciones del smartphone";
        float precio = 499.99f;
        String proveedorNick = "proveedor1";
        int stock = 50;

        // Agregar un producto
        s.agregarProducto(titulo, numRef, descripcion, especificaciones, precio, proveedorNick, stock);

        // Verificar que el producto se haya agregado correctamente
        Producto p = s.getProducto(numRef);
        Proveedor proveedor = p.getProveedor();
        assertNotNull("El proveedor debería existir.", proveedor);
        Producto producto = proveedor.obtenerProd(numRef);
        assertNotNull("El producto debería ser agregado al proveedor.", producto);
        assertEquals("El título del producto no coincide.", titulo, producto.getNombre());
    }
}

    