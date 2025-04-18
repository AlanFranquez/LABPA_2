package services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.ws.Endpoint;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class Publicador {
    private static final String PERSISTENCE_UNIT = "miUnidadPersistencia";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    private EntityManager em = emf.createEntityManager();
    private ISistema s = Factory.getSistema();
    private Endpoint endpoint;

    // ======================= MÉTODOS DE SERVICIO ======================= //

    /**
     * Publicar el servicio.
     */
    @WebMethod(exclude = true)
    public void publicar() {
        try {
            // Obtener la IP local de forma dinámica
            String ipServidor = obtenerIPLocal(); // IP de la máquina actual
            System.out.print(ipServidor);
            String url = "http://" + ipServidor + ":1234/publicador";

            // Publicar el servicio en la URL generada
            endpoint = Endpoint.publish(url, this);
            System.out.println("Servicio publicado en: " + url);
        } catch (Exception e) {
            // Manejo de cualquier error al publicar el servicio
            System.err.println("Error al publicar el servicio: " + e.getMessage());
            e.printStackTrace();
        }
    }


    


    /**
     * Detener el servicio.
     */
    @WebMethod(exclude = true)
    public void detener() {
        if (endpoint != null && endpoint.isPublished()) {
            endpoint.stop();
            System.out.println("Servicio detenido correctamente.");
        } else {
            System.out.println("El servicio no estaba en ejecución.");
        }
    }
    
    private String obtenerIPLocal() {
        try {
            // Obtener la dirección IP local de la máquina
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // En caso de error, mostrar un mensaje y usar "localhost"
            System.err.println("Error al obtener la dirección IP local: " + e.getMessage());
            return "localhost"; // Fallback a "localhost"
        }
    }


	// ALAN
	@WebMethod
	public void agregarImagenUsuario(String nickName, byte[] imagenBytes) {
	    
	    EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	    EntityManager prob = prueba.createEntityManager();
	    
	    Usuario cliente = prob.find(Usuario.class, nickName);
	    // Persistir el cambio
	    prob.getTransaction().begin();
	    
	    cliente.setImagen(Base64.getEncoder().encodeToString(imagenBytes));
	    prob.merge(cliente);
	    prob.getTransaction().commit();
	    prob.close();
	    prueba.close();
	    
	    System.out.println("Se agrego la imagen al usuario: " + nickName);
	}
	
	@WebMethod
	public void agregarImagenProducto(int numRef, byte[] imagenBytes) {
	    
	    EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	    EntityManager prob = prueba.createEntityManager();
	    
	    Producto prod = prob.find(Producto.class, numRef);
	    // Persistir el cambio
	    prob.getTransaction().begin();
	    
        String base64Image = Base64.getEncoder().encodeToString(imagenBytes);
	    
	    prod.agregarImagen(base64Image);
	    prob.merge(prod);
	    prob.getTransaction().commit();
	    prob.close();
	    prueba.close();
	    System.out.println("Se agrego la imagen al Producto: " + prod.getNombre());
	}
	
	@WebMethod
	public String obtenerImagenUsuario(String nickName) {
	    EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	    EntityManager prob = prueba.createEntityManager();

	    Usuario cliente = prob.find(Usuario.class, nickName);

	    if (cliente != null && cliente.getImagen() != null) {
	        System.out.println("Imagen encontrada para el usuario: " + nickName);
	        return cliente.getImagen();
	    } else {
	        System.out.println("No se encontró imagen para el usuario: " + nickName);
	        return null;
	    }
	}
	
	@WebMethod
	public Usuario[] listaUsuarios() {
		return s.listaUsuarios().toArray(new Usuario[0]);
	}
	
	@WebMethod
	public ReclamoDTO[] obtenerReclamos(String nickProveedor) {
	

	        // Crear la consulta
	        TypedQuery<Reclamo> query = em.createQuery(
	            "SELECT r FROM Reclamo r WHERE r.proveedor.nick = :nickProveedor", 
	            Reclamo.class
	        );
	        query.setParameter("nickProveedor", nickProveedor);
	        
	        List<Reclamo>reclamos = query.getResultList();
	        List<ReclamoDTO> dtos = new ArrayList<ReclamoDTO>();
	        
	        for(Reclamo r : reclamos) {
	        	ReclamoDTO dt = new ReclamoDTO(r.getTexto(), r.getAutor().getNick(), r.getProducto().getNombre(), r.getFechaFormat());
	        	
	        	dtos.add(dt);
	        }
	        System.out.println("Se obtuvieron los reclamos del proveedor: " + nickProveedor);
	        return dtos.toArray(new ReclamoDTO[0]);
	       		
	    
	}
	
	@WebMethod
	public void agregarValoracion(int puntaje, int numRef, String nickName) {
		try {
			obtenerCliente(nickName).agregarPuntaje(puntaje, numRef);
			 System.out.println("Se agrego una valoracion al producto" + obtenerProducto(numRef).getNombre() + " hecho por " + nickName);
		} catch (ProductoException e) {
			e.printStackTrace();
		}
	}

	@WebMethod
	public Producto obtenerProducto(int numRef) {
		Producto p = em.find(Producto.class, numRef);
		// System.out.println("Se obtuvo el producto: " + p.getNombre() + " Numero Referencia: " + numRef);
		return p;
	}
	
	@WebMethod
	public String idReclamo(Reclamo r) {
		System.out.println("Se Obtuvo el autor del reclamo");
		return r.getAutor().getNick();
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
	public void agregarCliente(String nombre, String nickname, String apellido, String correo, int dia, int mes, int anio, String pass, String confPass) throws UsuarioRepetidoException {
		System.out.println("Se registro un nuevo cliente con el nickname: " + nickname);
		
		DTFecha fecha = new DTFecha(dia, mes, anio);
		s.agregarCliente(nombre, nickname, apellido, correo, fecha, pass, confPass);
		
	}
	
	@WebMethod
	public void agregarProveedor(String nickname, String correo, String nombre, String apellido, int dia, int mes, int anio, String compania, String link, String pass, String confPass) throws UsuarioRepetidoException {
		DTFecha fecha = new DTFecha(dia, mes, anio);
		System.out.println("Se registro un nuevo proveedor con el nickname: " + nickname);
		s.agregarProveedor(nickname, correo, nombre, apellido, fecha, compania, link, pass, confPass);
		
		
	}
	
	

	
	@WebMethod
	public void agregarImagenesProd(int numRef, String img) {
		
		
		Producto p = obtenerProducto(numRef);
		p.agregarImagen(img);
		System.out.println("Se agrego una imagen al producto: " + obtenerProducto(numRef).getNombre() + "Numero Referencia: " + numRef);
		em.merge(p);
		
		
	}
	
	@WebMethod
	public void agregarCATPadre(String nombre) throws CategoriaException {
		System.out.println("Se registro una nueva Categoria Padre: " + nombre);
		s.agregarCategoria(nombre);
	}
	
	@WebMethod
	public void agregarCategoriaProducto(int numRef, String nombreCategoria) throws CategoriaException {
		System.out.println("Se agrego el producto a la categoria: " + nombreCategoria);
		s.agregarProductoCategoria(nombreCategoria, numRef);
	}
	
	@WebMethod
	public void agregarCATProducto(String nombre) throws CategoriaException {
		System.out.println("Se registo una nueva Categoria: " + nombre);
		s.agregarCategoriaConProductos(nombre);
	}
	
	@WebMethod
	public void asignarlePadreCategoria(String padre, String nombreCategoria) throws CategoriaException {
		System.out.println("Ahora la categoria " + padre + " es padre de la categoria " + nombreCategoria);
		s.asignarlePadreCategoria(padre, nombreCategoria);
	}

	@WebMethod
	public Integer imprimirNumRef(int numRef) {
		return obtenerProducto(numRef).crearDT().getNumRef();
	}
	
	@WebMethod
	public String imprimirEspec(int numRef) {
		return obtenerProducto(numRef).crearDT().getEspecs();
	}
	
	@WebMethod
	public String imprimirCats(int numRef) {
		return obtenerProducto(numRef).crearDT().getCategorias();
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
	public String imprimirAutor(int comentarioId, int numProd) {
		return this.obtenerProducto(numProd).getComentario(comentarioId).getAutor().crearDt().getNick();
	}
	
	@WebMethod
	public String imprimirTextoComentario(int comentarioId, int numProd) {
		return this.obtenerProducto(numProd).getComentario(comentarioId).getTexto();
	}
	
	@WebMethod
	public String imprimirFechaComentario(int comentarioId, int numProd) {
		LocalDateTime nuevaFecha = this.obtenerProducto(numProd).getComentario(comentarioId).getFecha();
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	    return nuevaFecha.format(formatter);
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
		System.out.println("Se obtuvo el DtProducto del producto: " + numRef);
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
		System.out.println("Se obtuvo la primera imagen del producto: " + p.getNombre());
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
		 System.out.println("Se agregaron " + cantidad + "a la orden: " + numeroOrden);
		s.agregarItemsAOrden(numeroOrden, numProducto, cantidad);
	}
	
	public void realizarCompraPRUEBA(List<Item> items, float precioTotal, String nickCliente) {
		Map<Integer, Item> prods = new HashMap<>();
		
		for(Item p: items) {
			prods.put(p.getProducto().getNumRef(), p);
		}
		
		OrdenDeCompra nuevaCompra = new OrdenDeCompra(prods, precioTotal);
		
		s.realizarCompra(nuevaCompra, nickCliente);
		obtenerCliente(nickCliente).agregarCompra(nuevaCompra);
		
        notificarCompra(nickCliente, nuevaCompra.getNumero());
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
		System.out.println("Se obtuvo el carrito del Cliente: " + nickUsuario);
		return carrito;
	}

	@WebMethod
	public Boolean comprobarCliente(String nick) {
		return s.getUsuario(nick).getTipo() == "cliente";
	}

	@WebMethod
	public List<Producto> obtenerProductos() {
		System.out.println("Se obtuvieron todos los productos de la base de datos");
		return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
	}

	@WebMethod
	public Usuario obtenerUsuario(String nick) {
		em = emf.createEntityManager();
		return em.find(Usuario.class, nick);
	}
	
	@WebMethod
    public void editarCantidadItem(int numRef, String nickCliente, int nuevaCantidad) {
        try {
        	EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        	EntityManager em2 = emf2.createEntityManager();
            Cliente cliente = obtenerCliente(nickCliente);
            
            
            em2.getTransaction().begin();
            if (cliente != null) {
                Carrito carrito = cliente.getCarrito();
                System.out.println("Se modifico la cantidad del Item de " + carrito.getItem(numRef).getCant() + "a " + nuevaCantidad);
                carrito.getItem(numRef).setCant(nuevaCantidad);
                em2.merge(carrito);
            } else {
                throw new Exception("Cliente no encontrado.");
            }
            
            em2.getTransaction().commit();
            em2.close();
            emf2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@WebMethod
	public Cliente obtenerCliente(String nick) {
		System.out.println("Se obtuvo el Cliente: " + nick);
		return (Cliente) em.find(Cliente.class, nick);
	}

	@WebMethod
	public Proveedor obtenerProveedor(String nick) {
		System.out.println("Se obtuvo el Proveedor: " + nick);
		return (Proveedor) em.find(Proveedor.class, nick);
	}
	
	@WebMethod
	public void agregarCompraCliente(String nick, int numero) {
		obtenerCliente(nick).agregarCompra(obtenerOrden(numero));
		System.out.println("Se agrego una orden al cliente: " + nick);
		em.merge(obtenerCliente(nick));
	}
	

	@WebMethod
	public Carrito obtenerCarritoCliente(String nick) {
		Cliente cl = em.find(Cliente.class, nick);
		System.out.println("Se obtuvo el carrito del Cliente: " + nick);
		return cl.getCarrito();
	}
	
	@WebMethod
	public Item prodsAItem(int cantidad, int numRef) {
		System.out.println("Se crearon " + cantidad + " Items del producto: " + obtenerProducto(numRef).getNombre() + "Numero Referencia: " + numRef);
		return new Item(cantidad, obtenerProducto(numRef));
	}
	
	
	
	@WebMethod
	public Proveedor obtenerProvDeProducto(int numRef) {

		
		Proveedor nuevoProv = obtenerProducto(numRef).getProveedor();
		System.out.println("Se obtuvo el proveedor del producto: " + obtenerProducto(numRef).getNombre() + "Numero Referencia: " + numRef);
		return nuevoProv;
		
	}

	@WebMethod
	public boolean comprobarSiProductoExisteCarrito(String nick, int numRef) {
		Carrito c = this.obtenerCarritoCliente(nick);
		System.out.println("Se comprobo si el producto ya existe en el carrito de: " + nick);
		return c.getItem(numRef) != null;
	}

	@WebMethod
	public void setCarritoCliente(String nickName, Carrito nuevoCarrito) {
		EntityManager em = emf.createEntityManager();
		Cliente cl = this.obtenerCliente(nickName);

		cl.setCarrito(nuevoCarrito);
		em.persist(nuevoCarrito);
		System.out.println("Se Creo un nuevo carrito para: " + nickName);
		em.close();
	}

	@WebMethod
	public String obtenerPrimeraImagenProducto(int numRef) {
		System.out.println("Se obtuvo la primera imagen del producto: " + obtenerProducto(numRef).getNombre() + "Numero Referencia: " + numRef);
		return this.obtenerProducto(numRef).crearDT().getImagenes().get(0);
	}

	@WebMethod
	public List<String> obtenerImagenesProducto(int numRef) {
		 EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		    EntityManager prob = prueba.createEntityManager();

		    Producto prod = prob.find(Producto.class, numRef);

		    if (prod != null && prod.getImagenes() != null) {
		        System.out.println("Imagen encontrada para el usuario: " + prod.getNombre());
		        return prod.getImagenes();
		    } else {
		        System.out.println("No se encontró imagen para el usuario: " + prod.getNombre());
		        return null;
		    }
	}
	
	@WebMethod
	public String obtenerImagenesProductoPRIMERA(int numRef) {
		 EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		    EntityManager prob = prueba.createEntityManager();

		    Producto prod = prob.find(Producto.class, numRef);

		    if (prod != null && prod.getImagenes() != null) {
		        System.out.println("Imagen encontrada para el usuario: " + prod.getNombre());
		        return prod.getImagenes().get(0);
		    } else {
		        System.out.println("No se encontró imagen para el usuario: " + prod.getNombre());
		        return null;
		    }
	}
	
	
	
	@WebMethod
	public String imprimirNumRefOrden(String nickname, int orden) {
		return String.valueOf(getCompra(orden, nickname).crearDT().getNumero());
	}
	
	@WebMethod
	public void realizarReclamo(int num, String mensaje, String cliente) throws ReclamoException {

		s.agregarReclamo(mensaje, LocalDateTime.now(), obtenerProducto(num), obtenerProvDeProducto(num), obtenerCliente(cliente));		
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
	
	@WebMethod
    public void agregarComentario(int comentarioId, String mensaje, String nickCliente, int numRef) {
       
		EntityManagerFactory prueba = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager prubem = prueba.createEntityManager();
		
		prubem.getTransaction().begin();
		
		
        
        Comentario nuevoComentario = new Comentario(comentarioId, mensaje, obtenerCliente(nickCliente), LocalDateTime.now());
        Producto p = obtenerProducto(numRef);
        Cliente c = obtenerCliente(nickCliente);
        
        p.agregarComentario(nuevoComentario);
        try {
			c.agregarComentario(nuevoComentario, nickCliente);
		} catch (ProductoException e) {
			e.printStackTrace();
		}

        System.out.println("El cliente " + nickCliente + "agrego un comentario al producto: " + obtenerProducto(numRef).getNombre());
        prubem.persist(nuevoComentario);
        prubem.merge(obtenerProducto(numRef));
        prubem.merge(obtenerCliente(nickCliente));
        prubem.flush();
        prubem.getTransaction().commit();
        
        prubem.close();
        prueba.close();
        
    } 
	
	@WebMethod
    public void agregarRespuesta(int comentarioPadre, String mensaje, int respuestaID, String nick) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Comentario respuesta = new Comentario(respuestaID, mensaje, obtenerCliente(nick), LocalDateTime.now());
		
		Comentario padre = em.find(Comentario.class, comentarioPadre);
		
		padre.agregarRespuesta(respuesta);
		
		System.out.println("El cliente " + nick + "respondio al comentario de " + padre.getAutor().getNick());
		em.persist(respuesta);
		em.merge(padre);
		em.merge(respuesta);
		em.flush();
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		
    }


    @WebMethod
    public Comentario[] listarComentarios(int numRef) {
        List<Comentario> coments = obtenerProducto(numRef).getComentarios();

        return coments.toArray(new Comentario[0]);
    }
    
    @WebMethod
    public Comentario[] listarRespuestas(int numRef, int comentarioId) {
        List<Comentario> coments = obtenerProducto(numRef).getComentario(comentarioId).getRespuestas();

        return coments.toArray(new Comentario[0]);
    }
 
    @WebMethod
    public String imprimirFechaRespuesta(int numRef, int comentarioId, int respuestaId) {
        LocalDateTime coments = obtenerProducto(numRef).getComentario(comentarioId).getRespuesta(numRef).getFecha();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = coments.format(formatter);
        
        return formattedDate;
    }
    
    @WebMethod
	public String getNickAutor(Comentario c) {
		return c.getAutor().crearDt().getNick();
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
    public OrdenDeCompra[] getOrdenesCliente(String c) {
        if (c == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
         List<OrdenDeCompra> ordenes = obtenerCliente(c).getOrdenes();
        return ordenes.toArray(new OrdenDeCompra[0]);
    }	
	
	@WebMethod
	public String getNickDTCliente(String c) {
		return em.find(Usuario.class, c).getNick();
	}
	
	@WebMethod
	public boolean getRecibirNotificacionesDTCliente(String c) {
		return em.find(Cliente.class, c).isRecibirNotificaciones();
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
	
	
	
	@WebMethod
	public List<String> getImagenesDTProd(Producto p){
		return p.getImagenes();
	}
	
	@WebMethod
	public int getNumDTProd(DtProducto p){
		return p.getNumRef();
	}
	
	@WebMethod
	public Boolean comproProducto(String nick, int id) {
		return obtenerCliente(nick).comproProducto(id);
	}
	
	@WebMethod
	public String getDescDTProd(DtProducto p) {
		return p.getDescripcion();
	}
	
	@WebMethod
	public float getPrecioDTProd(DtProducto p) {
		return p.getPrecio();
	}
	
	@WebMethod
	public List<Cat_Producto> getCategoriasDTProd(Producto p) {
		return p.getCategorias();
	}

	@WebMethod
	public String getEspecsDTProd(Producto p) {
		return p.getEspecificaciones();
	}
	
	@WebMethod
	public String getnickProvDTProd(int numRef) {
		return obtenerProducto(numRef).crearDT().getNicknameProveedor();
	}
	
	@WebMethod
	public int getStockDTProd(DtProducto p) {
		return p.getStock();
	}
	
	@WebMethod
	public Integer[] getPuntajeDTProd(int numRef) {
		return obtenerProducto(numRef).obtenerPuntaje();
	}
	
	@WebMethod
	public DTCliente getAutorComentario(Comentario c) {
		return c.getAutor().crearDt();
	}
	
	@WebMethod
	public String getImagenAutor(DTCliente c) throws IOException {
	     // Ruta relativa proporcionada por el servicio
	        String relativa = "media/" + c.getImagenes();

	        // Concatenar la carpeta 'media' a la ruta
	     // Obtener el recurso desde el classpath
	        ClassLoader classLoader = getClass().getClassLoader();
	        URL resourceUrl = classLoader.getResource(relativa);

	        // Crear un archivo con esa ruta
	        File imageFile = new File(resourceUrl.getFile()); // Cambia a la ruta de tu imagen
	        BufferedImage image = ImageIO.read(imageFile);

	        // Convertir la imagen a un arreglo de bytes
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(image, "jpg", baos);
	        byte[] imageBytes = baos.toByteArray();

	        // Codificar la imagen en Base64
	        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	        return base64Image;
	}
	
	@WebMethod
	public String getTextoCom(Comentario c) {
		return c.getTexto();
	}
	
	@WebMethod
	public String getfechaCom(Comentario c) {
		return c.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}
	
	@WebMethod
	public int getNumeroCom(Comentario c) {
		return c.getNumero();
	}
	
	@WebMethod
	public List<Comentario> getRespuestas(Comentario c) {
		return c.getRespuestas();
	}
	
	@WebMethod
	public Boolean existeProdCarrito(Carrito c, int num) {
		return c.existeProducto(num);
	}
	
	@WebMethod
	public String getNickPorDTCliente(DTCliente c) {
		return c.getNick();
	}
	
	@WebMethod
	public Producto[] obtenerProductosProveedor(String nick) {
		List<Producto> productos = null;
		String jpql = "SELECT p FROM Producto p WHERE p.proveedor.nick = :proveedorNick";
		productos = em.createQuery(jpql, Producto.class)
                .setParameter("proveedorNick", nick)  // Usar el valor del parámetro correctamente
                .getResultList();
		System.out.println("se obtuvieron todos los productos del proveedor: " + nick);
		return productos.toArray(new Producto[0]);
	}
	
	@WebMethod
	public String getSitioWeb(String c) {
		return em.find(Proveedor.class, c).getLink();
	}
	
	@WebMethod
	public String getCompania(String c) {
		return em.find(Proveedor.class, c).getCompania();
	}
	
	@WebMethod
	public List<Cliente> listarClientes() {
	    return em.createQuery("SELECT c FROM Cliente c WHERE c.tipo = 'cliente'").getResultList();
	}

	


	// FABRICIO
	/*
	 * wsimport -keep -p webservices http://localhost:1234/publicador?wsdl
	 * 
	 * 
	 * netstat -ano | findstr :1234
	 * 
	 * taskkill /PID <ID_proceso> /F
	 * 
	 */
	@WebMethod
	public Categoria[] getCategoriasLista(){
		List<Categoria> categorias = null;
	
			 categorias = s.getCategoriasLista();
		 
		
		return categorias.toArray(new Categoria[0]);
	}

	@WebMethod
	public void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones, float precio,
			String proveedor, int stock) {
		System.out.println("se creo un nuevo producto para el proveedor: " + proveedor);
		s.agregarProducto(titulo, numRef, descripcion, especificaciones, precio, proveedor, stock);
	}
	
	@WebMethod
	public void notificarClientesNuevoProducto(int numRef, String nick) {
		Producto prod = this.obtenerProducto(numRef);
		Proveedor prov = this.obtenerProveedor(nick);
		s.notificarClientesNuevoProducto(prod, prov);
	}
	@WebMethod
	public void agregarProductoCategoria(String catName, int numRef) throws CategoriaException {
		s.agregarProductoCategoria(catName, numRef);
	}

	@WebMethod
	public void realizarCompra(OrdenDeCompra ordenCompra, String nick) {
		s.realizarCompra(ordenCompra, nick);
		
	}
	
	@WebMethod
	public Categoria[] listaCatPRODS() {
		return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList().toArray(new Categoria[0]);
	}
	
	
	
	@WebMethod
	public List<Producto> buscarProductos(String searchQuery){
		return s.buscarProductos(searchQuery);
	}
	
	@WebMethod
	public boolean clienteTieneCarrito(String nick){
		Cliente cl = em.find(Cliente.class, nick);
		System.out.println("Se comprobo si " + nick + "tiene carrito");
		return cl.getCarrito().getProductos().isEmpty();
	}
	
	@WebMethod
	public Item[] getItemsCarrito(String nick){
		Carrito cl = obtenerCliente(nick).getCarrito();
		System.out.println("Se obtuvieron todos los productos del carrito de " + nick);
		return cl.getProductos().toArray(new Item[0]);
	}
	@WebMethod
	public Proveedor getProveedorItem(Long id){
		return em.find(Item.class, id).getProveedor();
	}
	
	@WebMethod
	public String imprimirAutorReclamo(Long id) {
		return em.find(Reclamo.class, id).getAutor().getNick();
	}
	
	@WebMethod
	public String imprimirTextoReclamo(Long id) {
		return em.find(Reclamo.class, id).getTexto();
	}
	
	
	@WebMethod
	public String imprimirNombreProdReclamo(Long id) {
		return em.find(Reclamo.class, id).getProducto().getNombre();
	}
	
	@WebMethod
	public String imprimirFechaReclamo(Long id) {
		return em.find(Reclamo.class, id).getFechaFormat();
	}
	
	@WebMethod
	public void vaciarCarrito(String nick){
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em2 = emf2.createEntityManager();
		em2.getTransaction().begin();
		Cliente cli = em2.find(Cliente.class, nick);
		Carrito carrito = cli.getCarrito();
		carrito.vaciarCarrito();
		System.out.println("se vacio el carrito del cliente: " + nick);
		em2.getTransaction().commit();
		em2.close();
		
		em.merge(carrito);
	}
	
	@WebMethod
	public Usuario agregarProveedor2(String nick, String correo, String nombre, String apellido, String fecha, String nombreCompania, String sitioWeb, String contraseña, String contraseña2) {
		String[] partesFecha = fecha.split("-");
        int anio = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]);
        int dia = Integer.parseInt(partesFecha[2]);
        DTFecha fechaNueva = new DTFecha(dia, mes, anio);
        
        try {
			s.agregarProveedor(nick, correo, nombre, apellido, fechaNueva, nombreCompania, sitioWeb, contraseña, contraseña2);
			System.out.println(nick + "es ahora un nuevo Proveedor!!");
		} catch (UsuarioRepetidoException e) {
			e.printStackTrace();
		}
        return em.find(Proveedor.class, nick);	
	}
	
	@WebMethod
	public Usuario agregarCliente2(String nombre, String nick, String apellido, String correo, String fecha, String contraseña, String contraseña2) {
		String[] partesFecha = fecha.split("-");
        int anio = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]);
        int dia = Integer.parseInt(partesFecha[2]);
        DTFecha fechaNueva = new DTFecha(dia, mes, anio);
        
        try {
			s.agregarCliente(nombre, nick, apellido, correo, fechaNueva, contraseña, contraseña2);
			System.out.println(nick + "es ahora un nuevo Cliente!!");
		} catch (UsuarioRepetidoException e) {
			e.printStackTrace();
		}
        return em.find(Cliente.class, nick);	
	}
	
	@WebMethod
	public void agregarImagenUsuarioString(String nick, String fileName) {
		System.out.println("se agrego una imagen para el usuario: " + nick);
		s.agregarImagenUsuario(nick, fileName);
	}
	
	// RENZO
	
		@WebMethod
	    public void eliminarProductoDelCarrito(int numRef, String nickCliente) {
	        try {
	        	EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	        	EntityManager em2 = emf2.createEntityManager();
	            Cliente cliente = obtenerCliente(nickCliente);
	            
	            
	            em2.getTransaction().begin();
	            if (cliente != null) {
	                Carrito carrito = cliente.getCarrito();
	                carrito.eliminarProd(numRef);
	                System.out.println("Se elimino el producto " + obtenerProducto(numRef).getNombre() + " del carrito de " + nickCliente);
	                em2.merge(carrito);
	            } else {
	                throw new Exception("Cliente no encontrado.");
	            }
	            
	            em2.getTransaction().commit();
	            em2.close();
	            emf2.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

		@WebMethod
	    public void agregarProductoAlCarrito(Item item, String nickCliente) {
			EntityManagerFactory fac = Persistence.createEntityManagerFactory("miUnidadPersistencia");
			EntityManager emfac = fac.createEntityManager();
			
			
			emfac.getTransaction().begin();
	            Cliente cliente = obtenerCliente(nickCliente);
	                Carrito carrito = cliente.getCarrito();
	                carrito.agregarProducto(item); 
	                System.out.println("El producto " + item.getProducto().getNombre() + "fue agregado al carrito de " + nickCliente);
	         emfac.merge(carrito);
	         emfac.getTransaction().commit();
	         emfac.close();
	         fac.close();
	         
	    }
		
		@WebMethod
		public List<Reclamo> obtenerReclamosProd(int numRef){
			Producto p = em.find(Producto.class, numRef);
			System.out.println("Se obtuvieron todos los reclamos del producto: " + obtenerProducto(numRef).getNombre());
			return p.getReclamos();
		}
		
		@WebMethod
		public List<Item> getProductosCarrito(String c) {
			if (c == null) {
		        throw new IllegalArgumentException("El cliente no puede ser nulo");
		    }
			System.out.println("Se obtuvieron todos los productos del carrito de " + c);
			return obtenerCarritoCliente(c).getProductos();
		}
		
		@WebMethod
		public Item obtenerItemCarrito(String nick, int numRef) {
		    Cliente cl = em.find(Cliente.class, nick);
		    Carrito carrito = cl.getCarrito();
		    System.out.println("Se obtuvo el Item del carrito: " + obtenerProducto(numRef).getNombre());
		    return carrito.getItem(numRef); 
		}
		
		@WebMethod
		public void notificarComentario(int numRef, int comId) {
			Producto p = s.getProducto(numRef);
			Comentario c = s.getComentario(comId);
			s.notificarComentario(p, c, null);
		}
		
		@WebMethod
		public void notificarCompra(String nick, int numRef) {
			Cliente cl = obtenerCliente(nick);
			OrdenDeCompra ord = em.find(OrdenDeCompra.class, numRef);
			
			s.notificarCompra(cl, ord);
		}
		
		@WebMethod
		public void notificarRespuestaComentario(int numRef, int comId, int resId) {
			Producto p = s.getProducto(numRef);
			Comentario c = s.getComentario(comId);
			Comentario r = s.getComentario(resId);
			s.notificarComentario(p, c, r);
		}
		
		@WebMethod
		public void setRecibirNotificaciones(String nick, boolean activar) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Cliente cliente = obtenerCliente(nick);
			cliente.setRecibirNotificaciones(activar);
			System.out.println("El cliente " + nick + " estableció recibirNotificaciones como " + activar);
	        
			em.merge(cliente);
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
		}
		
		@WebMethod
		public Cliente getClientePorToken(String token) {
			return s.getClientePorToken(token);
		}
		
		@WebMethod
		public void setTokenDesactivacionCliente(String nick, String token) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Cliente cliente = obtenerCliente(nick);
			cliente.setTokenDesactivacion(token);
			System.out.println("El cliente " + nick + " recibió un nuevo token de desactivación: " + token);
	        
			em.merge(cliente);
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
		}
		
		@WebMethod
		public void comprasUnicasProducto(int numRef) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Producto p = em.find(Producto.class, numRef);
			p.setComprasUnicas();
			em.merge(p);
			em.getTransaction().commit();
			em.close();
			emf.close();
		}
		
		@WebMethod
		public List<String> listarCategoriasProducto(){
			return s.listarSoloNombresCatProducto();
		}
		
		@WebMethod
		public Integer getComprasUnicasProducto(int numRef) {
			return this.obtenerProducto(numRef).getComprasUnicas();
		}
		
		@WebMethod
		public void saveImageForUser(String nick, byte[] imageBytes) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();

	        try {
	            Usuario usuario = em.find(Usuario.class, nick);
	            
	            if (usuario != null) {
	            	
	                usuario.setImagen(Base64.getEncoder().encodeToString(imageBytes));
	                em.merge(usuario);
	                em.getTransaction().commit();
	            } else {
	                // Si el usuario no existe, hacer rollback y notificar al usuario
	                em.getTransaction().rollback();
	                throw new Exception("Usuario no encontrado");
	            }
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	            if (emf != null) {
	                emf.close();
	            }
	        }
		}
		@WebMethod
		public void saludarDesdeSvCentral(String texto) {
			App.saludar(texto);
		}
		public String saludar() {
			return "Hola Mundo";
		}
}
