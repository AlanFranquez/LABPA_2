package com.model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.exceptions.*;

import javax.swing.tree.DefaultMutableTreeNode;

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
		} catch (UsuarioRepetidoException e) { }
		
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
		} catch (UsuarioRepetidoException e) { }
		
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
	public void testAgregarCategorias() {
		try {
			s.agregarCategoria("cat1");
		} catch (CategoriaException e) { }
		try {
			s.agregarCategoria("cat1");
		} catch (CategoriaException e) {
			System.out.print("testAgregarCategorias: " + e.getMessage() + "\n");
		}
		
		try {
			s.agregarCategoriaConProductos("cat2");
		} catch (CategoriaException e) { }
		try {
			s.agregarCategoriaConProductos("cat1");
		} catch (CategoriaException e) {
			System.out.print("testAgregarCategorias: " + e.getMessage() + "\n");
		}
	}
	
	@Test
	public void testGetCat1() {
		Categoria cat = s.getCat("nom");
		assertEquals(null, cat);
	}
	@Test
	public void testGetCat2() {
		Categoria cat = s.getCat("cat1");
		assertEquals("cat1", cat.getNombre());
	}
	
	@Test
	public void testGetCategorias() {
		try {
			s.agregarCategoria("cat1");
			s.agregarCategoriaConProductos("cat2");
		} catch (CategoriaException e) { }
		Categoria[] cats= s.getCategorias();
		Categoria[] esperado= new Categoria[2];
		esperado[0] = s.getCat("cat2");
		esperado[1] = s.getCat("cat1");
		assertArrayEquals(esperado,cats);
	}
	
	@Test
	public void testAsignarlePadreCategoria() {
		try {
			s.asignarlePadreCategoria("cat1", "cat2");
		} catch (CategoriaException e) { }
		try {
			s.asignarlePadreCategoria("cat1", "cat2");
		} catch (CategoriaException e) {
			System.out.print("testAsignarlePadreCategoria: " + e.getMessage() + "\n");
		}
		try {
			s.asignarlePadreCategoria("cat2", "cat2");
		} catch (CategoriaException e) {
			System.out.print("testAsignarlePadreCategoria: " + e.getMessage() + "\n");
		}
	}
	
	@Test
	public void testListarSoloNombresPadresCat() {
		List<String> cats= s.listarSoloNombresPadresCat();
		List<String> esperado = new ArrayList<>();
		s.arbolCategorias();
		esperado.add("cat1");
		boolean eq = cats.equals(esperado);
		assertEquals(true,eq);
	}
	
	@Test
	public void testVerificarUnicidadProducto() {
		try {
			s.agregarProducto("titulo1", 1, "descripcion", "especificaciones", 200, "nick1", 10);
			s.agregarProducto("titulo2", 2, "descripcion", "especificaciones", 200, "nick1", 10);
			s.agregarProductoCategoria("cat2", 1);
			s.arbolProductos();
		} catch (CategoriaException e) { }
		
		boolean resultado = s.verificarUnicidadProducto("cat2", 2, "titulo2");
		assertEquals(true, resultado);
	}
	
	@Test
	public void testVerificarUnicidadProductoDiferentes() {
		try {
			s.agregarProducto("titulo1", 1, "descripcion", "especificaciones", 200, "nick1", 10);
			s.agregarProducto("titulo2", 2, "descripcion", "especificaciones", 200, "nick1", 10);
			s.agregarProductoCategoria("cat2", 1);
		} catch (CategoriaException e) { }
		
		boolean resultado = s.verificarUnicidadProducto("cat2", 2, "titulo1");
		assertEquals(false, resultado);
	}
	

    @Test
    public void testAgregarProductoCorrecto() {
        String titulo = "Smartphone";
        int numRef = 67890;
        String descripcion = "Un smartphone de última generación";
        String especificaciones = "Especificaciones del smartphone";
        float precio = 499;
        String proveedorNick = "nick1";
        int stock = 50;

        // Agregar un producto
        s.agregarProducto(titulo, numRef, descripcion, especificaciones, precio, proveedorNick, stock);

        // Verificar que el producto se haya agregado correctamente
        Producto p = s.getProducto(numRef);
        s.obtenerStockProducto(numRef);
        Proveedor proveedor = p.getProveedor();
        assertNotNull("El proveedor debería existir.", proveedor);
        Producto producto = proveedor.obtenerProd(numRef);
        assertNotNull("El producto debería ser agregado al proveedor.", producto);
    }
    
    @Test
    public void testEsPadre() {
    	assertEquals(s.esPadre("cat2"), false);
    }
    
    @Test
    public void testOrdenCompra() {
    	s.CrearOrden();
    	s.eliminarUltimaOrden();
    	s.CrearOrden();
    	s.CrearOrden();
    	s.asignarOrdenCliente("nick2");
    	int i = s.obtenerStockProducto(1);
    	assertEquals(10, i);
    }
    
    @Test
    public void testListarClientes() {
    	try {
        	DTFecha f = new DTFecha(1, 1, 1111);
			s.agregarCliente("nombre", "nick2", "apellido", "correo2", f, "contra", "contra");
		} catch (UsuarioRepetidoException e) { }
    	List<DTCliente> clientes = s.listarClientes();
    	List<DTCliente> esperado = new ArrayList<>();
    	Cliente u = (Cliente) s.getUsuario("nick2");
    	esperado.add(u.crearDt());
    	boolean eq = esperado.equals(clientes);
    	assertEquals(false, eq);
    }
}

    