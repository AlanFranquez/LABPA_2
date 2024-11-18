package services;

import java.util.ArrayList;
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
import com.market.svcentral.Categoria;
import com.market.svcentral.Cliente;
import com.market.svcentral.DTCliente;
import com.market.svcentral.DTEstado;
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
@WebService
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
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
	public Producto obtenerProducto(int numRef) {
		return em.find(Producto.class, numRef);
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
	public Carrito obtenerCarritoCliente(String nick) {
		Cliente cl = em.find(Cliente.class, nick);
		
		return cl.getCarrito();
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
		return this.obtenerProducto(p.getNumRef()).getImagenes().get(0);
	}
	
	@WebMethod
	public List<String> obtenerImagenesProducto(Producto p) {
		return this.obtenerProducto(p.getNumRef()).getImagenes();
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
		public OrdenDeCompra getCompra(int num, Cliente c) {
			return c.getCompra(num);
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
		public String getTipo(Usuario u) {
			return u.getTipo();
		}
		
		@WebMethod
		public int getNumRefOrden(DTOrdenDeCompra o) {
			return o.getNumero();
		}
		
		@WebMethod
		public List<Item> getItemsOrden(DTOrdenDeCompra o) {
		    return o.getItems().values().stream().collect(Collectors.toList());
		}
		
		@WebMethod
		public List<OrdenDeCompra> listarCompras(Cliente c) {
		    
			
		    return c.getOrdenes();
			
		}
		
		@WebMethod
		public List<DTEstado> getHistorialEstado(DTOrdenDeCompra o){
			return o.getHistorialEstado();
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
		public String getEstadoOrden(DTOrdenDeCompra o) {
			return o.getEstado();
		}
		
		@WebMethod
		public float getPrecioTotalOrden(DTOrdenDeCompra o) {
			return o.getPrecioTotal();
		}
		
		@WebMethod
		public String getFechaOrden(DTOrdenDeCompra o) {
			return o.getFechaString();
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
		public List<OrdenDeCompra> getOrdenesCliente(Cliente c) {
			if (c == null) {
		        throw new IllegalArgumentException("El cliente no puede ser nulo");
		    }
			
			
			
			return c.getOrdenes();
		}
		
		@WebMethod
		public String getNickDTCliente(DTCliente c) {
			return c.getNick();
		}
		
		@WebMethod
		public String getNombreDTCliente(DTCliente c) {
			return c.getNombre();
		}
		
		@WebMethod
		public String getApellidoDTCliente(DTCliente c) {
			return c.getApellido();
		}
		
		@WebMethod
		public String getImagenesDTCliente(DTCliente c) {
			return c.getImagenes();
		}
		
		@WebMethod
		public String getFechaNacDTClienteString(DTCliente c) {
			return c.getNacimientoFormateado();
		}
		
		@WebMethod
		public DTOrdenDeCompra crearDTOrden(OrdenDeCompra o) {
			return o.crearDT();
		}
	// FABRICIO
	/*
	  	wsimport -keep -p webservices http://localhost:1234/publicador?wsdl
	  	
	  	Quitale lo del -p webservices, asi queda en la carpeta de services D:
	  
		ProductoServlet.java
		RealizarCompra.java
		RealizarReclamo.java
		RegistrarUsuarios1.java
		RegistrarUsuarios2.java
		Saludo.java
		Usuarios.java
		ValidarAjax.java
		VerReclamo.java 
	 */
	@WebMethod
	public List<Categoria> getCategoriasLista() {
		return s.getCategoriasLista();
	}
	
	@WebMethod
	public void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones, float precio, String proveedor, int stock) {
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
