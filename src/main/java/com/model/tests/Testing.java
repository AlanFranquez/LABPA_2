package com.model.tests;
import java.time.LocalDateTime;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
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
    public void testListarALLProductosVacio() {
		try {
			s.listarALLProductos();
		} catch (ProductoException e) {
			System.out.print("testListarALLProductosVacio: " + e.getMessage() + "\n");
		}
    }
	
	@Test
    public void testAgregarProveedor() {
		DTFecha f = new DTFecha(1, 1, 1111);
		
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
		} catch (CategoriaException e) {
			System.out.print("testAgregarCategorias: " + e.getMessage() + "\n");
		}
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
		Cat_Padre cat = (Cat_Padre) s.getCat("cat1");
		DTCat_Padre dt = cat.crearDT();
		assertEquals("cat1", dt.getNombre());
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
			s.agregarProducto("TITULO1", 1, "descripcion", "especificaciones", 200, "nick1", 10);
			s.agregarProducto("titulo2", 2, "descripcion", "especificaciones", 200, "nick1", 10);
			List<Producto> lista = s.getAllProductos();
	    	assertEquals(lista.size(), 1);
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
        DtProducto dt = s.getDtProducto(numRef);
        try {
			s.agregarProductoCategoria("cat2", numRef);
		} catch (CategoriaException e) { }
        s.borrarProducto(numRef, titulo);
    }
    
    @Test
    public void testEsPadre() {
    	assertEquals(s.esPadre("cat2"), false);
    }
    
    @Test
    public void testOrdenCompra() {
    	OrdenDeCompra o = new OrdenDeCompra(0);
    	LocalDateTime f = o.getFecha();
    	assertEquals(f.getMonth(), LocalDateTime.now().getMonth());
    	o.setNumero(1);
    	assertEquals(1, o.getNumero());
    	o.setNumero(0);
    	Producto p = s.getProducto(1);
    	o.addItem(p, 1);
    	o.addItem(p, 1);
    	
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
			Usuario u = s.getUserByEmail("correo2");
			Usuario esperado = s.getUsuario("nick2");
			assertEquals(u, esperado);
		} catch (UsuarioRepetidoException | UsuarioException e) { }
    	List<DTCliente> clientes = s.listarClientes();
    	List<DTCliente> esperado = new ArrayList<>();
    	Cliente u = (Cliente) s.getUsuario("nick2");
    	esperado.add(u.crearDt());
    	boolean eq = esperado.equals(clientes);
    	assertEquals(false, eq);
    }
    
    @Test
    public void testListarProveedores() {
    	try {
        	DTFecha f = new DTFecha(1, 1, 1111);
        	s.agregarProveedor("nick1", "correo1", "nombre", "apellido", f, "compania", "link", "contra", "contra");
		} catch (UsuarioRepetidoException e) { }
    	List<DTProveedor> clientes = s.listarProveedores();
    	List<DTProveedor> esperado = new ArrayList<>();
    	Proveedor u = (Proveedor) s.getUsuario("nick1");
    	esperado.add(u.crearDt());
    	boolean eq = esperado.equals(clientes);
    	assertEquals(false, eq);
    }
    
    @Test
    public void testGetClienteDeOrden() {
    	try {
    		s.CrearOrden();
    		s.existeOrden(1);
    		s.asignarOrdenCliente("nick2");
			s.eliminarOrdenDeCompra(1);
		} catch (OrdenDeCompraException e) { }
    }
    
    @Test
    public void testGetClienteDeOrdenInexistente() {
    	try {
			s.eliminarOrdenDeCompra(0);
		} catch (OrdenDeCompraException e) {
			System.out.print("testGetClienteDeOrdenInexistente: " + e.getMessage() + "\n");
		}
    }
    @Test
    public void testGetClienteDeOrdenUsuarioInexistente() {
    	try {
    		s.CrearOrden();
			s.eliminarOrdenDeCompra(2);
		} catch (OrdenDeCompraException e) {
			System.out.print("testGetClienteDeOrdenUsuarioInexistente: " + e.getMessage() + "\n");
		}
    }
    
    @Test
    public void testListarProductosPorCategoria() {
    	try {
    		List<DtProducto> prods = s.listarProductosPorCategoria("cat2");
    		assertEquals(prods.size(), 1);
		} catch (ProductoException e) { }
    }
    @Test
    public void testListarProductosPorCategoriaVacia() {
    	try {
    		s.agregarCategoriaConProductos("cat3");
    		s.listarProductosPorCategoria("cat3");
		} catch (ProductoException | CategoriaException e) {
			System.out.print("testListarProductosPorCategoriaVacia: " + e.getMessage() + "\n");
		}
    }
    
    @Test
    public void testGetProdByCateogria() {
    	try {
			Producto prod = s.getProdByCateogria("cat2", 1);
			assertEquals(prod.getNumRef(), 1);
			s.getProdByCateogria("cat3", 1);
		} catch (ProductoException e) {
			System.out.print("testGetProdByCateogria: " + e.getMessage() + "\n");
		}
    }
    @Test
    public void testGetProdByCateogria2() {
    	try {
    		s.agregarProducto("titulo3", 3, "desc", "esp", 10, "nick1", 10);
			s.getProdByCateogria("cat2", 3);
    	} catch (ProductoException e) {
			System.out.print("testGetProdByCateogria2: " + e.getMessage() + "\n");
		}
    }
    
    @Test
    public void testListarALLProductos() {
		try {
			List<DtProducto> lista = s.listarALLProductos();
			assertEquals(lista.size(), 1);
		} catch (ProductoException e) { }
    }
    
    @Test
    public void testGetDtProductoNull() {
    	DtProducto dt = s.getDtProducto(1000);
    	assertNull(dt);
    }
    @Test
    public void testGetProductoNull() {
    	Producto p= s.getProducto(1000);
    	assertNull(p);
    }
    
    @Test
    public void testExistenOrdenesParaListar() {
    	boolean eq = s.existenOrdenesParaListar();
    	assertEquals(eq, false);
    	s.CrearOrden();
    	eq = s.existenOrdenesParaListar();
    	assertEquals(eq, true);
    	
    	List<DTOrdenDeCompra> l = s.listarOrdenes();
    	l.getFirst().getItems();
    	assertEquals(l.size(), 1);
    	s.eliminarUltimaOrden();
    	l = s.listarOrdenes();
    	assertEquals(l.size(), 0);
    }
    
    @Test
    public void testAddOrdenes() {
    	s.CrearOrden();
    	OrdenDeCompra ord = s.getOrden(1);
    	s.addOrdenes(ord, "nick2");
    }
    
    @Test
    public void testComprobarCat() {
		try {
			s.agregarCategoriaConProductos("cat20");
			s.comprobarCat("cat20");
			s.comprobarCat("cat100");
		} catch (CategoriaException e) {
			System.out.print("testComprobarCat: " + e.getMessage() + "\n");
		}
    }
    
    @Test
    public void testEliminarPDesdeProveedor() {
    	s.agregarProducto("titulo1", 100, "descripcion", "especificaciones", 200, "nick1", 10);
    	s.eliminarPDesdeProveedor("nick1", 100);
    	Producto p = s.getProducto(100);
    	assertEquals(null, p);
    }
    
    @Test
    public void testMostrarPerfilCliente() {
    	DTCliente dt = s.mostrarPerfilCliente("nick2");
    	assertEquals(dt.getNick(), "nick2");
    	DTProveedor dt2 = s.mostrarPerfilProveedor("nick1");
    	assertEquals(dt2.getNick(), "nick1");
    }
    
    @Test
    public void testGetOrdenesCliente () {
    	List<DTOrdenDeCompra> lista = s.getOrdenesCliente("nick2");
    	assertEquals(lista.size(), 1);
    }
    
    @Test
    public void testGetUserByEmail() {
    	try {
			Usuario u = s.getUserByEmail("otro");
		} catch (UsuarioException e) {
			System.out.print("testGetUserByEmail: " + e.getMessage() + "\n");
		}
    }
    
    @Test
    public void testagregarImagenes() {
    	s.agregarImagenesDesdeProveedor("nick1", 1, "img");
    	s.agregarImagenesDesdeProveedor("otro", 1, "img");
    	s.agregarImagenesDesdeProveedor("nick1", 1000, "img");
    }
    
    @Test
    public void testGetCategoriasLista() {
    	Map<String, Categoria> lista = s.getCategoriasLista();
    	assertEquals(lista.size(), 4);
    }
    
    @Test
    public void testbuscarProductos() {
    	List<Producto> lista = s.buscarProductos("titulo");
    	assertEquals(lista.size(), 1);
    	lista = s.buscarProductos("TItULO");
    	assertEquals(lista.size(), 1);
    }
    
   
    
    @Test
    public void testrealizarCompra() {
    	s.CrearOrden();
    	s.asignarOrdenCliente("nick2");
    	OrdenDeCompra ord = s.getOrden(1);
    	Producto p = new Producto("nom", "des", 10, 12345, "esp", null, 10);
    	ord.addItem(p, 3);
    	s.realizarCompra(ord, "nick2");
    	s.eliminarUltimaOrden();
    	s.realizarCompra(ord, "nick232");
    }
    
    @Test
    public void testListaUsuarios() {
    	List<Usuario> lista = s.listaUsuarios();
    	assertEquals(lista.size(), 2);
    }
    
    @Test
    public void testCambiarEstadoOrden() {
    	s.CrearOrden();
    	s.asignarOrdenCliente("nick2");
    	s.cambiarEstadoOrden("estado", 1, "nick2");
    	s.eliminarUltimaOrden();
    	s.cambiarEstadoOrden("estado", 1, "otro");
    }
    
    
    // Cliente
    @Test
    public void testearGetCompras() {
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	
    	OrdenDeCompra o1 = new OrdenDeCompra(1);
    	OrdenDeCompra o2 = new OrdenDeCompra(2);
    	
    	cl.agregarCompra(o1);
    	cl.agregarCompra(o2);
    	
    	assertEquals(2, cl.getAllOrdenes().size());
    }
    
    @Test
    public void testearGetCompra() {
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	
    	OrdenDeCompra o1 = new OrdenDeCompra(1);
    	OrdenDeCompra o2 = new OrdenDeCompra(2);
    	
    	cl.agregarCompra(o1);
    	cl.agregarCompra(o2);
    	
    	assertEquals(o1, cl.getCompra(1));
    }

  
    
    @Test
    public void testCrearProducto() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	
    	Producto producto = new Producto("Producto1", "Descripción del producto", 		29.99f, 123, "Especificaciones aquí", p1, 10);
    	
    	
        
        assertNotNull(producto);
        assertEquals("Producto1", producto.getNombre());
        assertEquals(29.99f, producto.getPrecio(), 0.01);
        assertEquals(10, producto.getStock());
    }
    
    @Test
    public void testAgregarComentario() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	
    	Producto producto = new Producto("Producto1", "Descripción del producto", 		29.99f, 123, "Especificaciones aquí", p1, 10);
    	
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	
    	LocalDateTime fecha = LocalDateTime.now();
    	
    	Comentario c = new Comentario(1, "Buen Producto", cl, fecha);
    	producto.agregarComentario(c);
        
        assertNotNull(producto.getComentarios());
        assertEquals(c, producto.getComentario(1));
    }
    
    @Test
    public void testAgregarImagen() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        String imagen = "imagen1.jpg";
        producto.agregarImagen(imagen);
        
        assertEquals(1, producto.getImagenes().size());
        assertEquals(imagen, producto.getImagenes().get(0));
    }
    
    @Test
    public void testEliminarCategorias() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Cat_Producto categoria1 = new Cat_Producto("Categoria1");
        Cat_Producto categoria2 = new Cat_Producto("Categoria2");
        producto.agregarCategorias(categoria1);
        producto.agregarCategorias(categoria2);
        
        producto.eliminarCategorias();
        assertTrue(producto.getCategorias().isEmpty());
    }
    
    @Test
    public void testCrearDtProducto() {
    	
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        DtProducto dtProducto = producto.crearDT();
        
        assertNotNull(dtProducto);
        assertEquals("Producto1", dtProducto.getNombre());
        assertEquals("Descripción del producto", dtProducto.getDescripcion());
        assertEquals(30, dtProducto.getPrecio());
        assertEquals(123, dtProducto.getNumRef());
        assertEquals(p1.getNick(), dtProducto.getNicknameProveedor());
        assertTrue(dtProducto.getCategorias().contains("El producto no tiene categorias asignadas"));
    }
    
    @Test
    public void testCrearDTCliente() {
    	
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	
    	
        assertNotNull(cl);
        assertEquals("andres", "andres");
    }
    
    @Test
    public void testCrearItem() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12,12, 2001), "comp", "link", "123");
    	Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto);
        
        assertNotNull(it);
        assertEquals(producto, it.getProducto());
        assertEquals(5, it.getCant());
    }
    
    @Test
    public void testSubCantidadIT() {
        float precio = 30;
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", precio, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto);
        
        float precioSub = precio * 5;
        
        assertEquals(precioSub, it.getSubTotal(), 0.001); 
    }
    
    
    @Test
    public void generarOrdenDeCompra() {
        OrdenDeCompra o = new OrdenDeCompra(1);
        
        
        Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	cl.agregarCompra(o);
    	
    	assertEquals(o, cl.getCompra(1));
    }
    
    @Test
    public void agregarItem() {
        OrdenDeCompra o = new OrdenDeCompra(1);
        
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	cl.agregarCompra(o);
    	
    	o.addItem(producto,5);
    	
    	assertEquals(1, o.getItems().size());
    }
    
    
    @Test
    public void testRemoverItem() {
        OrdenDeCompra o = new OrdenDeCompra(1);
        
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	cl.agregarCompra(o);
    	
    	o.addItem(producto,5);
    	o.removeItem(producto.getNumRef());
    	
    	assertEquals(0, o.getItems().size());
    	assertNull(o.getItems().get(producto.getNumRef()));
    }
    
    @Test
    public void testComprobarEstado() {
        OrdenDeCompra o = new OrdenDeCompra(1);
        
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	cl.agregarCompra(o);
    	
    	assertNotNull(o.getEstado());
    }
    
    @Test
    public void testDTOrden() {
        OrdenDeCompra o = new OrdenDeCompra(1);
        
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        DTOrdenDeCompra ordendt = o.crearDT();
        
        
        assertEquals(ordendt.getNumero(), o.getNumero());
        assertEquals(ordendt.getEstado(), o.getEstado());
    }

    
    @Test
    public void testCambiarEstado() {
        
        
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        
        Map<Integer, Item> items = new HashMap<Integer, Item>();
        Item it = new Item(5, producto);
        items.put(producto.getNumRef(), it);
        
        OrdenDeCompra o = new OrdenDeCompra(items, 150);
        
        o.setEstado("otro estado");
        
        assertEquals(o.getEstado(), "otro estado");
    }
    
    @Test
    public void testDTItem() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        
        
        Map<Integer, Item> items = new HashMap<Integer, Item>();
        Item it = new Item(5, producto);
        
        DTItem dtit = it.crearDT();
        
        assertEquals(it.getCant(), dtit.getCant());
        assertEquals(it.getProducto(), dtit.getProducto());
    }
    
   

    @Test
    public void testAgregarProductoExito() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto); 
        Carrito carrito = new Carrito();
        
        carrito.agregarProducto(it);
        assertTrue(carrito.existeProducto(123));
    }


    @Test
    public void testEliminarProducto() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto); 
        Carrito carrito = new Carrito();
        
        carrito.agregarProducto(it);
        carrito.eliminarProd(it.getProducto().getNumRef());
        assertFalse(carrito.existeProducto(1));
    }

    @Test
    public void testCarritoAgregar() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto); 
        Carrito carrito = new Carrito();
        
        carrito.agregarProducto(it);
        carrito.eliminarProd(it.getProducto().getNumRef());
        assertFalse(carrito.existeProducto(1));
    }

    @Test
    public void testProductoExsite() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto); 
        Carrito carrito = new Carrito();
        
        carrito.agregarProducto(it);
        assertEquals(true, carrito.existeProducto(it.getProducto().getNumRef()));
    }
    
    @Test
    public void testCrearDTProveedor() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
    	assertEquals(p1.crearDt().getNombre(), p1.getNombre());
    }
    
    @Test
    public void testDTProveedorGetAtributos() {
    	Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        
    	
    	assertEquals(p1.crearDt().getNombre(), p1.getNombre());
    	assertEquals(p1.crearDt().getNick(), p1.getNick());
    	assertEquals(p1.crearDt().getApellido(), p1.getApellido());
    	assertEquals(p1.crearDt().getCompania(), p1.getCompania());
    	assertEquals(p1.crearDt().getLink(), p1.getLink());
    	assertEquals(p1.crearDt().cantProd(), 0);
    }
    
    @Test
    public void testDTCliente() {
    	DTFecha f = new DTFecha(12,12, 2001);
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", f, "123");
    	DTCliente dt = cl.crearDt();
    	dt.toString();
    	dt.toStringCompleto();
    	assertEquals(f.getDia() + " / " + f.getMes() + " / " + f.getAnio(), dt.getNacimientoFormateado());
    	assertEquals("Cliente", dt.getTipo());
    	assertEquals(null, dt.getImagenes());
    	assertEquals(f, dt.getNacimiento());
    	assertEquals(dt, dt.mostrarPerfil());
    	assertEquals(null, dt.getOrdenes());
    }
    
    @Test
    public void testDTClienteGets() {
    	Cliente cl = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	
    	assertEquals(cl.crearDt().getNombre(), cl.getNombre());
    	assertEquals(cl.crearDt().getNick(), cl.getNick());
    	assertEquals(cl.crearDt().getApellido(), cl.getApellido());
    	assertEquals(cl.crearDt().getCorreo(), cl.crearDt().getCorreo());
    }
    
    @Test
    public void testEstadoSesionValues() {
        assertEquals(3, EstadoSesion.values().length);
        assertEquals(EstadoSesion.NO_LOGIN, EstadoSesion.valueOf("NO_LOGIN"));
        assertEquals(EstadoSesion.LOGIN_CORRECTO, EstadoSesion.valueOf("LOGIN_CORRECTO"));
        assertEquals(EstadoSesion.LOGIN_INCORRECTO, EstadoSesion.valueOf("LOGIN_INCORRECTO"));
    }

    @Test
    public void testEstadoSesionOrder() {
        EstadoSesion estado = EstadoSesion.LOGIN_CORRECTO;
        assertTrue(estado.ordinal() > EstadoSesion.NO_LOGIN.ordinal());
        assertTrue(estado.ordinal() < EstadoSesion.LOGIN_INCORRECTO.ordinal());
    }
    
    private DTCat_Padre categoria;

    @BeforeEach
    public void setUp1() {
        categoria = new DTCat_Padre("Categoría 1");
    }

    @Test
    public void testConstructor() {
        assertEquals("Categoría 1", categoria.getNombre());
    }

    @Test
    public void testSetNombre() {
        categoria.setNombre("Categoría 2");
        assertEquals("Categoría 2", categoria.getNombre());
    }

    @Test
    public void testSetNombre_Null() {
        categoria.setNombre(null);
        assertNull(categoria.getNombre());
    }
    
    private Administrador admin;
    private DTFecha fechaNacimiento;

    @BeforeEach
    public void setUp2() {
        fechaNacimiento = new DTFecha(1990, 1, 1);
        admin = new Administrador("Nombre", "nickAdmin", "Apellido", "correo@example.com", fechaNacimiento, "contrasena123");
    }

    @Test
    public void testAdministradorCreacion() {
        assertNotNull(admin);
        assertEquals("Nombre", admin.getNombre()); 
        assertEquals("nickAdmin", admin.getNick());
        assertEquals("Apellido", admin.getApellido());
        assertEquals("correo@example.com", admin.getCorreo());
        assertEquals(fechaNacimiento, admin.getNacimiento()); 

    }

    @Test
    public void testContrasena() {
        assertEquals("contrasena123", admin.getContrasena());
    }
    
    @Test
    public void testGetSistema() {
        ISistema sistema = Factory.getSistema();
        assertNotNull(sistema);
        assertTrue(sistema instanceof Sistema);
    }
    
    private DTOrdenDeCompra orden;
    private Map<Integer, Item> items;
    
    @BeforeEach
    public void setUp3() {
        items = new HashMap<>();
        // Crea un Item de ejemplo y añádelo al mapa
        Proveedor p1 = new Proveedor("nombre", "nick3", "apellido", "correo", new DTFecha(12, 12, 2001), "comp", "link", "123");
        Producto producto = new Producto("Producto1", "Descripción del producto", 30, 123, "Especificaciones aquí", p1, 10);
        Item it = new Item(5, producto); // Cantidad 2
        items.put(1, it);
        orden = new DTOrdenDeCompra(123, items, 200.0f, "Pendiente");
    }

    @Test
    public void testGetNumero() {
        assertEquals(123, orden.getNumero());
    }

    @Test
    public void testGetPrecioTotal() {
        assertEquals(200.0f, orden.getPrecioTotal());
    }

    @Test
    public void testGetEstado() {
        assertEquals("Pendiente", orden.getEstado());
    }

    @Test
    public void testListarItems() {
        assertEquals(1, orden.listarItems().size());
    }

    @Test
    public void testGetFecha() {
        LocalDateTime fecha = orden.getFecha();
        assertNotNull(fecha);
        assertEquals(LocalDateTime.now().getDayOfYear(), fecha.getDayOfYear()); 
    }

    @Test
    public void testGetFechaString() {
        String fechaString = orden.getFechaString();
        assertTrue(fechaString.matches("\\d{2}-\\d{2}-\\d{4}")); 
    }

    @Test
    public void testToString() {
        String resultado = orden.toString();
        assertTrue(resultado.contains("Orden número = 123"));
        assertTrue(resultado.contains("precioTotal = 200.0"));
    }
    
    @Test
    public void testFecha() {
    	DTFecha f = new DTFecha(20, 5, 2000);
    	assertEquals(20, f.getDia());
    	assertEquals(5, f.getMes());
    	assertEquals(2000, f.getAnio());
    }
    
    @Test
    public void testSetItems() {
    	Item i = new Item(4, s.getProducto(1));
    	i.setCant(1);
    	i.setProducto(s.getProducto(2));
    	assertEquals(1, i.getCant());
    	assertEquals(s.getProducto(2), i.getProducto());
    	
    	DTItem dt = i.crearDT();
    	dt.setCant(1);
    	dt.setProducto(s.getProducto(2));
    	assertEquals(1, dt.getCant());
    	assertEquals(s.getProducto(2), dt.getProducto());
    	assertEquals(200.0, dt.getSubTotal());
    }
    
    @Test
    public void testCategoria() {
    	Cat_Producto c = new Cat_Producto("nom");
    	Cat_Padre p = new Cat_Padre("p");
    	p.agregarHijo(c);
    	c.recorrerCategorias(p, new ArrayList<>());
    	c.setNombre("otro");
    	c.setTipo("tipo");
    	Producto prod = new Producto("titulo2", "descripcion", 200, 123, "especificaciones", (Proveedor) s.getUsuario("nick1"), 10);
    	c.agregarProducto(prod);
    	assertFalse(c.verificarProducto(123, "titulo2"));
    	assertEquals("otro", c.getNombre());
    	assertEquals("tipo", c.getTipo());
    }
    
    @Test
    public void testDtProducto() {
    	Producto prod = new Producto("titulo2", "descripcion", 200, 123, "especificaciones", (Proveedor) s.getUsuario("nick1"), 10);
    	DtProducto dt = prod.crearDT();
    	assertEquals(s.getUsuario("nick1").getNombre(), dt.getNombreProveedor());
    	assertEquals(0, dt.getCantidadComprada());
    	assertEquals(new ArrayList<>(), dt.getComentarios());
    	assertEquals(10, dt.getStock());
    	assertEquals("especificaciones", dt.getEspecs());
    	assertEquals(null, dt.getPrimeraImagen());
    	dt.agregarImagen("img");
    	dt.getImagenes();
    	assertEquals("img", dt.getPrimeraImagen());
    }
    
    @Test
    public void testUsuario() {
    	DTFecha f = new DTFecha(1, 2, 3);
    	Cliente c = new Cliente("andres", "andres123", "perez", "perez@gmail.com", new DTFecha(12,12, 2001), "123");
    	c.setApellido("ap");
    	assertEquals("ap", c.getApellido());
    	c.setContrasena("cont");
    	assertEquals("cont", c.getContrasena());
    	c.setCorreo("cor");
    	assertEquals("cor", c.getCorreo());
    	c.setNacimiento(f);
    	assertEquals(f, c.getNacimiento());
    	c.setNick("ni");
    	assertEquals("ni", c.getNick());
    	c.setNombre("nom");
    	assertEquals("nom", c.getNombre());
    }
}

    