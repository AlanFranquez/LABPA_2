package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.ws.Endpoint;

import com.market.svcentral.Carrito;
import com.market.svcentral.Cat_Padre;
import com.market.svcentral.Cat_Producto;
import com.market.svcentral.Categoria;
import com.market.svcentral.Cliente;
import com.market.svcentral.DTCliente;
import com.market.svcentral.DTEstado;
import com.market.svcentral.DTFecha;
import com.market.svcentral.DTItem;
import com.market.svcentral.DTOrdenDeCompra;
import com.market.svcentral.DtProducto;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Item;
import com.market.svcentral.OrdenDeCompra;
import com.market.svcentral.Producto;
import com.market.svcentral.Proveedor;
import com.market.svcentral.Usuario;
import com.market.svcentral.exceptions.CategoriaException;
import com.market.svcentral.exceptions.ProductoException;
import com.market.svcentral.exceptions.UsuarioRepetidoException;

@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class Publicador {
	private Endpoint endpoint = null;
	ISistema s = Factory.getSistema();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	EntityManager em = emf.createEntityManager();

	List<Producto> productos = null;

	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:1234/publicador", this);
	}

	public Publicador() {
		s = Factory.getSistema();
	}

	@WebMethod(exclude = true)
	public Endpoint getEndPoint() {
		return this.endpoint;
	}

	// ALAN
	
	@WebMethod
	public void agregarValoracion(int puntaje, int numRef, String nickName) {
		try {
			obtenerCliente(nickName).agregarPuntaje(puntaje, numRef);
		} catch (ProductoException e) {
			e.printStackTrace();
		}
	}

	@WebMethod
	public Producto obtenerProducto(int numRef) {
		return em.find(Producto.class, numRef);
	}

	@WebMethod
	public DTFecha nuevaFecha(int dia, int mes, int anio) {
		return new DTFecha(dia, mes, anio);
	}
	
	@WebMethod
	public DTEstado nuevoEstado(String nombre, String comentarios) {
		return new DTEstado(nombre, comentarios);
	}

	@WebMethod
	public void agregarCliente(String nombre, String nickname, String apellido, String correo, 
			DTFecha fecha, String pass, String confPass) throws UsuarioRepetidoException {
		
		s.agregarCliente(nombre, nickname, apellido, correo, fecha, pass, confPass);
		
	}
	
	@WebMethod
	public void agregarProveedor(String nickname, String correo, String nombre, String apellido, DTFecha fecha, String compania, String link, String pass, String confPass) throws UsuarioRepetidoException {
		
		
		s.agregarProveedor(nickname, correo, nombre, apellido, fecha, compania, link, pass, confPass);
		
		
	}
	
	
	
	@WebMethod
	public void agregarImagenUsuario(String nickname, String imagen) {

		
		Usuario u = obtenerUsuario(nickname);
		u.setImagen(imagen);
		
		em.merge(u);
		
		
	}
	
	@WebMethod
	public void agregarImagenesProd(int numRef, String img) {
		
		
		Producto p = obtenerProducto(numRef);
		p.agregarImagen(img);
		
		em.merge(p);
		
		
	}
	
	@WebMethod
	public void agregarCATPadre(String nombre) throws CategoriaException {
		s.agregarCategoria(nombre);
	}
	
	@WebMethod
	public void agregarCategoriaProducto(int numRef, String nombreCategoria) throws CategoriaException {
		s.agregarProductoCategoria(nombreCategoria, numRef);
	}
	
	@WebMethod
	public void agregarCATProducto(String nombre) throws CategoriaException {
		s.agregarCategoriaConProductos(nombre);
	}
	
	@WebMethod
	public void asignarlePadreCategoria(String padre, String nombreCategoria) throws CategoriaException {
		s.asignarlePadreCategoria(padre, nombreCategoria);
	}

	@WebMethod
	public Integer imprimirNumRef(int numRef) {
		return obtenerProducto(numRef).crearDT().getNumRef();
	}
	
	@WebMethod
	public Integer imprimirCantidad(int numeroOrden, String nick, int numeroProd) {
			Map<Integer, Item> items = getCompra(numeroOrden, nick).getItems();
		
		
		for(Map.Entry<Integer, Item> entry : items.entrySet()) {
			if(entry.getValue().getProducto().getNumRef() == numeroProd) {
				return entry.getValue().getCant();
			}
		}
		
		return 0;
	}
	
	@WebMethod
	public float imprimirSubTotal(int numeroOrden, String nick, int numeroProd) {
			Map<Integer, Item> items = getCompra(numeroOrden, nick).getItems();
		
		
		for(Map.Entry<Integer, Item> entry : items.entrySet()) {
			if(entry.getValue().getProducto().getNumRef() == numeroProd) {
				return entry.getValue().getSubTotal();
			}
		}
		
		return 0;
	}

	@WebMethod
	public String imprimirNombreProd(int numRef) {
		return obtenerProducto(numRef).crearDT().getNombre();
	}

	@WebMethod
	public String imprimirDescripcion(int numRef) {
		return obtenerProducto(numRef).crearDT().getDescripcion();
	}

	@WebMethod
	public float imprimirPrecioProd(int numRef) {
		return obtenerProducto(numRef).crearDT().getPrecio();
	}

	@WebMethod
	public float imprimirStock(int numRef) {
		return obtenerProducto(numRef).crearDT().getStock();
	}

	@WebMethod
	public DtProducto obtenerDTProducto(int numRef) {
		Producto p1 = obtenerProducto(numRef);

		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•";
		String catStr = "";
		if (p1.getCategorias().isEmpty()) {
			catStr = "El producto no tiene categorias asignadas";
		}
		for (Cat_Producto cat : p1.getCategorias()) {
			catStr = catStr + "<br>" + tab + cat.getNombre();
			Cat_Padre cPadre = cat.getPadre();
			while (cPadre != null) {
				catStr = catStr + " -> " + cPadre.getNombre();
				cPadre = cPadre.getPadre();
			}
		}
		catStr = catStr + "</html>";
		return new DtProducto(p1.getNombre(), p1.getDescripcion(), p1.getPrecio(), p1.getNumRef(),
				p1.getEspecificaciones(), p1.getProveedor(), catStr, p1.getImagenes(), p1.getStock(),
				p1.getComentarios(), p1.getCantidadComprada(), p1.getReclamos(), p1.obtenerPuntajes());
	}

	@WebMethod
	public DTCliente obtenerDTCliente(String nick) {
		return obtenerCliente(nick).crearDt();
	}

	@WebMethod
	public String obtenerPrimeraIMGProd(Producto p) {
		return this.obtenerProducto(p.getNumRef()).getImagenes().get(0);
	}

	@WebMethod
	public String verificarClienteYCrearCarrito(String nickUsuario) {
		EntityManager em = emf.createEntityManager();
		String resultado;
		try {
			em.getTransaction().begin();

			Usuario usuarioLogueado = em.find(Usuario.class, nickUsuario);
			if (usuarioLogueado instanceof Cliente) {
				Cliente clienteLogueado = (Cliente) usuarioLogueado;
				System.out.println("Cliente logueado: " + clienteLogueado.getNick());

				if (clienteLogueado.getCarrito() == null) {
					System.out.println("Carrito no encontrado para el cliente, creando nuevo carrito.");
					Carrito nuevoCarrito = new Carrito();
					clienteLogueado.setCarrito(nuevoCarrito);
					em.persist(nuevoCarrito);
					resultado = "Nuevo carrito creado para el cliente: " + clienteLogueado.getNick();
				} else {
					resultado = "Cliente ya tiene un carrito con ID: " + clienteLogueado.getCarrito().getId();
				}

				em.getTransaction().commit();
			} else {
				resultado = "El usuario no es un cliente.";
			}
		} catch (Exception e) {
			resultado = "Error al verificar el cliente: " + e.getMessage();
			e.printStackTrace();
		} finally {
			em.close();
		}

		return resultado;
	}
	
	@WebMethod
	public OrdenDeCompra iniciarOrden(String nickProveedor) {
		Integer ord = s.iniciarOrdenVacia(nickProveedor);
	    
	   
	   return em.find(OrdenDeCompra.class, ord);
	}
	
	@WebMethod
	public OrdenDeCompra obtenerOrden(int numeroOrden) {
	    OrdenDeCompra orden = em.find(OrdenDeCompra.class, numeroOrden);
	    
	    System.out.println("SE TRAJO LA ORDEN: " + orden.getNumero());
	    return orden;
	}
	
	
	@WebMethod
	public List<Item> imprimirITemsORDENS(int numeroOrden, String nick) {
		
		Map<Integer, Item> items = getCompra(numeroOrden, nick).getItems();
		
		List<Item> prods = new ArrayList<Item>();
		
		for(Map.Entry<Integer, Item> entry : items.entrySet()) {
			Item it = entry.getValue();
			
			prods.add(it);
		}
		
		return prods;
	}
	
	@WebMethod
	public void agregarItemsAOrden(int numeroOrden, int numProducto, int cantidad) {
		s.agregarItemsAOrden(numeroOrden, numProducto, cantidad);
	}
	
	public void realizarCompraPRUEBA(List<Item> items, float precioTotal, String nickProveedor, String nickCliente) {
		Map<Integer, Item> prods = new HashMap<>();
		
		for(Item p: items) {
			prods.put(p.getProducto().getNumRef(), p);
		}
		
		OrdenDeCompra nuevaCompra = new OrdenDeCompra(prods, precioTotal, obtenerProveedor(nickCliente));
		
		s.realizarCompra(nuevaCompra, nickCliente);
		obtenerCliente(nickCliente).agregarCompra(nuevaCompra);
	}

	@WebMethod
	public Carrito obtenerCarritoDeCliente(String nickUsuario) {
		EntityManager em = emf.createEntityManager();
		Carrito carrito = null;
		try {
			Usuario usuarioLogueado = em.find(Usuario.class, nickUsuario);
			if (usuarioLogueado instanceof Cliente) {
				Cliente clienteLogueado = (Cliente) usuarioLogueado;
				carrito = clienteLogueado.getCarrito();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return carrito;
	}

	@WebMethod
	public Boolean comprobarCliente(String nick) {
		return s.getUsuario(nick).getTipo() == "cliente";
	}

	@WebMethod
	public List<Producto> obtenerProductos() {
		return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
	}

	@WebMethod
	public Usuario obtenerUsuario(String nick) {
		return em.find(Usuario.class, nick);
	}

	@WebMethod
	public Cliente obtenerCliente(String nick) {
		return (Cliente) em.find(Cliente.class, nick);
	}

	@WebMethod
	public Proveedor obtenerProveedor(String nick) {
		return (Proveedor) em.find(Proveedor.class, nick);
	}
	
	@WebMethod
	public void agregarCompraCliente(String nick, int numero) {
		obtenerCliente(nick).agregarCompra(obtenerOrden(numero));
		
		em.merge(obtenerCliente(nick));
	}
	

	@WebMethod
	public Carrito obtenerCarritoCliente(String nick) {
		Cliente cl = em.find(Cliente.class, nick);

		return cl.getCarrito();
	}
	
	@WebMethod
	public Item prodsAItem(int cantidad, int numRef) {
		return new Item(cantidad, obtenerProducto(numRef));
	}
	
	
	
	@WebMethod
	public Proveedor obtenerProvDeProducto(int numRef) {

		
		Proveedor nuevoProv = obtenerProducto(numRef).getProveedor();

		return nuevoProv;
		
	}

	@WebMethod
	public boolean comprobarSiProductoExisteCarrito(String nick, int numRef) {
		Carrito c = this.obtenerCarritoCliente(nick);

		return c.getItem(numRef) != null;
	}

	@WebMethod
	public void setCarritoCliente(String nickName, Carrito nuevoCarrito) {
		EntityManager em = emf.createEntityManager();
		Cliente cl = this.obtenerCliente(nickName);

		cl.setCarrito(nuevoCarrito);
		em.persist(nuevoCarrito);

		em.close();
	}

	@WebMethod
	public String obtenerPrimeraImagenProducto(Producto p) {
		return this.obtenerProducto(p.getNumRef()).crearDT().getImagenes().get(0);
	}

	@WebMethod
	public List<String> obtenerImagenesProducto(Producto p) {
		return this.obtenerProducto(p.getNumRef()).crearDT().getImagenes();
	}
	
	@WebMethod
	public String imprimirNumRefOrden(String nickname, int orden) {
		return String.valueOf(getCompra(orden, nickname).crearDT().getNumero());
	}
	
	@WebMethod
	public String imprimirEstadoOrden(String nickName, int orden) {
		return getCompra(orden, nickName).crearDT().getEstado();
	}
	
	@WebMethod
	public float imprimirPrecioTotal(String nickName, int orden) {
		return getCompra(orden, nickName).getPrecioTotal();
	}
	
	@WebMethod
	public String imprimirFechaOrden(String nickName, int orden) {
		return getCompra(orden, nickName).crearDT().getFechaString();
	}
	
	@WebMethod
	public String imprimirFechaCliente(String nickName) {
		DTFecha fecha = obtenerCliente(nickName).getNacimiento();
		
		return fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio();
	}

	// CARLITOS
	@WebMethod
	public DTOrdenDeCompra mostrarCompraCliente(Cliente c, int numO) {
		return c.mostrarCompras(numO);
	}
			
	@WebMethod
	public DTCliente crearDTCliente(Cliente c) {
		return c.crearDt();
	}
			
	@WebMethod
	public void setEstadoOrden(OrdenDeCompra o, DTEstado e) {
		o.setEstado(e);
		em.merge(o);
	}
			
	@WebMethod
	public String getNickCliente(Cliente c) {
		return c.getNick();
	}
			
	@WebMethod
	public String getNickxDTCliente(DTCliente c) {
		return c.getNick();
	}
	
	@WebMethod
	public OrdenDeCompra getCompra(int num, String nickName) {
		return obtenerCliente(nickName).obtenerOrden(num);
	}
	
	@WebMethod
	public String getNombreUsuario(Usuario u) {
		return u.getNick();
	}
	
	@WebMethod
	public DTEstado crearEstado(String estado, String com) {
		return new DTEstado(estado, com);
	}
			
			
	@WebMethod
	public String getTipo(String u) {
		return em.find(Usuario.class, u).getTipo();
	}
			
	@WebMethod
	public int getNumRefOrden(int o) {
		return em.find(OrdenDeCompra.class, o).crearDT().getNumero();
	}
			
	@WebMethod
	public List<Item> getItemsOrden(OrdenDeCompra o) {
	    return o.getItems().values().stream().collect(Collectors.toList());
	}
			
			//@WebMethod
			//public List<OrdenDeCompra> listarComprasPorNick(String nick) {
			  //  Cliente cliente = obtenerCliente(nick);
			    //if (cliente == null) {
			      //  return null;
			   // }
			    //return cliente.getCompras().values().stream().collect(Collectors.toList());
		//	}
			
	@WebMethod
	public List<DTEstado> getHistorialEstado(int numeroOrden, String nickCliente){
		return obtenerCliente(nickCliente).getCompra(numeroOrden).getHistorialEstado();
	}
			
	@WebMethod
	public String getEstado(DTEstado e) {
		return e.getEstado();
	}
			
	@WebMethod
	public String getFechaEstado(DTEstado e) {
		return e.getFecha();
	}
			
	@WebMethod
	public String getComEstado(DTEstado e) {
		return e.getComentarios();
	}
			
	@WebMethod
	public String getEstadoOrden(int o, String nick) {
		return getCompra(o, nick).getEstado();
	}
	
	@WebMethod
	public void setEstado(int numeroOrden, String nickname, String nombreEstado, String comentario) {
		DTEstado nuevoEstado = new DTEstado(nombreEstado, comentario);
		getCompra(numeroOrden, nickname).setEstado(nuevoEstado);
	}
	
	@WebMethod
	public void setEstadoPrueba(String nickname, String nombreEstado, String comentario) {
		obtenerCliente(nickname).getCompras().get(0).setEstado(new DTEstado(nombreEstado, comentario));
	}
			
	@WebMethod
	public float getPrecioTotalOrden(int o) {
		return em.find(OrdenDeCompra.class, o).crearDT().getPrecioTotal();
	}
			
	@WebMethod
	public String getFechaOrden(int o) {
		return em.find(OrdenDeCompra.class, o).crearDT().getFechaString();
	}
			
	@WebMethod
	public DTItem crearDTItem(Item i) {
		return i.crearDT();
	}
			
	@WebMethod
	public Producto getProductoItem(DTItem i) {
		return i.getProducto();
	}
			
	@WebMethod
	public DtProducto crearDTProd(Producto p) {
		return p.crearDT();
	}
			
	@WebMethod
	public String getNombreProd(DtProducto p) {
		return p.getNombre();
	}
			
	@WebMethod
	public float getPrecioProd(DtProducto p) {
		return p.getPrecio();
	}
	
	@WebMethod
	public int getCantProdItem(DTItem i) {
		return i.getCant();
	}
			
	@WebMethod
	public float getSubTotaItem(DTItem i) {
		return i.getSubTotal();
	}
			
	@WebMethod
	public List<OrdenDeCompra> getOrdenesCliente(String c) {
		if (c == null) {
	        throw new IllegalArgumentException("El cliente no puede ser nulo");
	    }
		
		return obtenerCliente(c).getOrdenes();
	}
			
	@WebMethod
	public String getNickDTCliente(String c) {
		return em.find(Usuario.class, c).getNick();
	}
			
	@WebMethod
	public String getNombreDTCliente(String c) {
		return em.find(Usuario.class, c).getNombre();
	}
			
	@WebMethod
	public String getApellidoDTCliente(String c) {
		return em.find(Usuario.class, c).getApellido();
	}
			
	@WebMethod
	public String getImagenesDTCliente(String c) {
		return em.find(Usuario.class, c).getImagen();
	}
			
	@WebMethod
	public String getFechaNacDTClienteString(String c) {
		return String.valueOf(obtenerCliente(c).crearDt().getNacimientoFormateado());
	}
	
	@WebMethod
	public DTOrdenDeCompra crearDTOrden(int numref) {
		return em.find(OrdenDeCompra.class, numref).crearDT();
	}

	// FABRICIO
	/*
	 * wsimport -keep -p webservices http://localhost:1234/publicador?wsdl
	 * 
	 * 
	 * ProductoServlet.java RealizarCompra.java RealizarReclamo.java
	 * RegistrarUsuarios1.java RegistrarUsuarios2.java Saludo.java Usuarios.java
	 * ValidarAjax.java VerReclamo.java
	 */
	@WebMethod
	public List<Categoria> getCategoriasLista() {
		return s.getCategoriasLista();
	}

	@WebMethod
	public void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones, float precio,
			String proveedor, int stock) {
		s.agregarProducto(titulo, numRef, descripcion, especificaciones, precio, proveedor, stock);
	}

	@WebMethod
	public void agregarProductoCategoria(String catName, int numRef) throws CategoriaException {
		s.agregarProductoCategoria(catName, numRef);
	}

	// RENZO

	public String saludar() {
		return "Hola Mundo";
	}

}
