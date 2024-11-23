package com.market.svcentral;

import java.awt.Image;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.market.svcentral.exceptions.CategoriaException;
import com.market.svcentral.exceptions.OrdenDeCompraException;
import com.market.svcentral.exceptions.ProductoException;
import com.market.svcentral.exceptions.ReclamoException;
import com.market.svcentral.exceptions.UsuarioException;
import com.market.svcentral.exceptions.UsuarioRepetidoException;

public class Sistema implements ISistema {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	EntityManager em = emf.createEntityManager();
	
    private static Sistema instance = null;
    private final EmailService emailService = new EmailService();
    
    private Sistema() {
    }

    public static synchronized Sistema getInstance() {
        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
    }

    // CASO DE USO 1: REGISTRAR USUARIO
	public boolean verificarUnicidad(String nick, String correo) {
    	Usuario usuario = null;
    	try {
    		usuario = em.find(Usuario.class, nick);
    		if (usuario == null) {
    			usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.correo='" + correo +"'", Usuario.class).getSingleResult();
    		}
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
        return usuario == null;
    }
    public void agregarProveedor(String nick, String correo, String nombre, String apellido, DTFecha fechaNacimiento, String compania, String link, String contra, String confContra) throws UsuarioRepetidoException {
    	if (!verificarUnicidad(nick, correo)) {
    		throw new UsuarioRepetidoException("Ya existe un usuario con nick: " + nick + " orden email: " + correo);
    	}
    	if (!contra.equals(confContra)) {
    		throw new UsuarioRepetidoException("Contraseñas Diferentes");
    	}
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	em.persist(new Proveedor(nombre, nick, apellido, correo, fechaNacimiento, compania, link, contra));
    	
    	em.getTransaction().commit();
    	em.close();
    }
    public void agregarCliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contra, String confContra) throws UsuarioRepetidoException {
    	if (!verificarUnicidad(nick, correo)) {
    		throw new UsuarioRepetidoException("Ya existe un usuario con este nick o correo");
    	}
    	if (!contra.equals(confContra)) {
    		throw new UsuarioRepetidoException("Contraseñas Diferentes");
    	}
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	em.persist(new Cliente(nombre, nick, apellido, correo, fecha, contra));
    	
    	em.getTransaction().commit();
    	em.close();
    }
    public void agregarImagenUsuario(String nick, String imagen) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	Usuario user = em.find(Usuario.class, nick);
    	if (user == null) {
    		System.out.println("Usuario con nick: " + nick + " no encontrado.");
    		return;
    	}
    	user.setImagen(imagen);
    	
    	em.getTransaction().commit();
    	em.close();
    }
    public Usuario getUsuario(String nickname) {
    	return em.find(Usuario.class, nickname);
    }
    public boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    
    
    // CASO DE USO 2: REGISTRAR PRODUCTO
    public boolean verificarUnicidadProducto(int numRef, String titulo) {
        Producto prod = null;
        try {
        	prod = em.find(Producto.class, numRef);
        	if(prod == null) {
        		prod = em.createQuery(
        				"SELECT p FROM Producto p WHERE p.nombre = '" + titulo + "'", Producto.class)
        				.getSingleResult();
        	}
        }catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return prod == null;
    }
    public void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones, float precio, String prov, int stock) {   
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        Proveedor proveedor = em.find(Proveedor.class, prov);
        if (proveedor == null) {
            System.out.println("Proveedor no encontrado: " + prov);
            return; 
        }
        Producto producto = new Producto(titulo, descripcion, precio, numRef, especificaciones, proveedor, stock);
        try {
        	proveedor.agregarProd(producto);
        	em.getTransaction().begin();
        	
        	em.persist(producto);
        	
        	em.getTransaction().commit();
        	em.close();
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }
    public DefaultMutableTreeNode arbolCategorias() {
    	List<Categoria> categorias = null;
	
    	try {
    		categorias = em.createQuery("SELECT c FROM Categoria c WHERE c.padre IS NULL", Categoria.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	
      	 DefaultMutableTreeNode root = new DefaultMutableTreeNode("Cats");
      	 for (Categoria cat : categorias) {
      		 DefaultMutableTreeNode child = arbolCategorias(cat);
      		 root.add(child);
      	 }
      	 return root;
    }
    public DefaultMutableTreeNode arbolCategorias(Categoria cat) {
        DefaultMutableTreeNode rama = new DefaultMutableTreeNode(cat.getNombre());
        if (cat.getTipo().equals("Padre")) {
            Map<String, Categoria> hijos = ((Cat_Padre) cat).getHijos();
            if (hijos.size() >= 1) {
                for (Map.Entry<String, Categoria> entry : hijos.entrySet()) {
                	Categoria hijo = entry.getValue();
                    DefaultMutableTreeNode child = arbolCategorias(hijo);
                    rama.add(child);
                }
            } else {
                rama.add(new DefaultMutableTreeNode("Sin Elementos"));
            }
        }
		return rama;
    }
    public boolean esPadre(String nombre) {
        Categoria cat = em.find(Categoria.class, nombre);
        if(cat == null) {
        	return false;
        }
    	return (cat.getTipo().equals("Padre"));
    }
    public void agregarProductoCategoria(String catName, int numRef) throws CategoriaException {
        try {
        	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        	EntityManager em = emf.createEntityManager();
        	em.getTransaction().begin();
        	
        	Cat_Producto cat = em.find(Cat_Producto.class, catName);
        	Producto producto = em.find(Producto.class, numRef);
        	
        	System.out.println(cat + " - " + cat != null + " - " + catName);
        	
        	producto.agregarCategorias(cat);
        	cat.agregarProducto(producto);
        	
        	em.getTransaction().commit();
        	em.close();
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }
    public void agregarImagenProd(String img, int numRef) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Producto producto = em.find(Producto.class, numRef);
        producto.agregarImagen(img);
        
        em.getTransaction().commit();
        em.close();
    }
    
    
    public Categoria getCat(String nombre) {
        return em.find(Categoria.class, nombre);
    }
    
    public Categoria[] getCategorias() {
        Collection<Categoria> collection = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
        return collection.toArray(new Categoria[collection.size()]); // Convierte a un arreglo
    }
    
    // CASO DE USO 3: ALTA DE CATEGORIA
    public void agregarCategoria(String nombre) throws CategoriaException {
	   if (existeCategoria(nombre)) {
		   throw new CategoriaException("El nombre de la categoria ya existe");
	   }
	   EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	   EntityManager em = emf.createEntityManager();
	   em.getTransaction().begin();
	   
	   em.persist(new Cat_Padre(nombre));
	   
	   em.getTransaction().commit();
	   em.close();
    }
    public void agregarCategoriaConProductos(String nombre) throws CategoriaException{
	   if (existeCategoria(nombre)) {
		   throw new CategoriaException("Esta categoria ya existe");
	   }
	   EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	   EntityManager em = emf.createEntityManager();
	   em.getTransaction().begin();
	   
	   em.persist(new Cat_Producto(nombre));
	   
	   em.getTransaction().commit();
	   em.close();
   }
    public void asignarlePadreCategoria(String nombrePadre, String nombre) throws CategoriaException {
	   if (nombre == nombrePadre) {
		   throw new CategoriaException("Una categoría no puede ser su propio padre");
	   }
	   EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	   EntityManager em = emf.createEntityManager();
	   em.getTransaction().begin();
	   
	   Cat_Padre catPadre = null;
	   Categoria cat = null;
   	
	   try {
		   catPadre = em.find(Cat_Padre.class, nombrePadre);
		   cat = em.find(Categoria.class, nombre);
	   } catch (Exception e) {
		   em.close();
		   e.printStackTrace();
	   }
	   if(cat == null || catPadre == null) {
		   em.close();
		   throw new CategoriaException("Una de las categorias ingresadas no existe");
	   }
	   if (catPadre.verificarSiYaEsHijo(nombre)) {
		   em.close();
		   throw new CategoriaException("Esta categoria ya es su hijo");
	   }
	   
	   cat.setPadre(catPadre);
	   catPadre.agregarHijo(cat);
	   
	   em.getTransaction().commit();
	   em.close();
   }

    public List <String> listarSoloNombresPadresCat() {
    	List<Cat_Padre> categorias = null;
    	
    	try {
    		categorias = em.createQuery("SELECT c FROM Cat_Padre c", Cat_Padre.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	List <String> listarPadres = new ArrayList<>();
    	for (Cat_Padre cat : categorias) {
    		listarPadres.add(cat.getNombre());    		
    	}
    	return listarPadres;
    }
    
    public boolean existeCategoria(String nombre) {
    	Categoria cat = null;
    	try {
    		cat = em.find(Categoria.class, nombre);
    	}catch (Exception e) {
    		System.out.print(e);
    	}
        return cat != null;
    }
    
    
    
    // CASO DE USO 4: GENERAR ORDEN DE COMPRA
    public DefaultMutableTreeNode arbolProductos() {
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Cats");
    	List<Categoria> categorias = null;
	
    	try {
    		categorias = em.createQuery("SELECT c FROM Categoria c WHERE c.padre IS NULL", Categoria.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
   	 	for (Categoria cat : categorias) {
   	 		DefaultMutableTreeNode child = arbolProductos(cat);
   	 		root.add(child);
   	 	}
   	 	return root;
    }
    
    public DefaultMutableTreeNode arbolProductos(Categoria cat) {
        DefaultMutableTreeNode rama = new DefaultMutableTreeNode(cat.getNombre());
        if (cat.getTipo().equals("Padre")) {
            Map<String, Categoria> hijos = ((Cat_Padre) cat).getHijos();
            if (hijos.size() >= 1) {
                for (Categoria hijo : hijos.values()) {
                    DefaultMutableTreeNode child = arbolProductos(hijo);
                    rama.add(child);
                }
            } else {
                rama.add(new DefaultMutableTreeNode("Sin Elementos"));
            }
        } else {
        	List<Producto> productos = null;
    		try {
    			productos = em.createQuery("SELECT p FROM Producto p JOIN p.categorias c WHERE c.nombre='" + cat.getNombre() + "'", Producto.class).getResultList();
    		} catch (Exception e) {
    			System.out.print(e);
    		}
            if (productos.size() >= 1) {
                for (Producto producto : productos) {
                    int stock = producto.getStock(); // Obtener el stock
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(
                        producto.getNombre() + " - " + producto.getNumRef() + " (" + stock + " disponibles)"
                    );
                    rama.add(child);
                }
            } else {
                rama.add(new DefaultMutableTreeNode("Sin Elementos"));
            }
        }
		return rama;
    }

    public void CrearOrden() {
    	//int maxKey = ordenes.keySet().stream().max(Integer::compare).orElse(0);
    	//OrdenDeCompra orden = new OrdenDeCompra(maxKey + 1);
    	//ordenes.put(orden.getNumero(), orden);
    }
    public Integer obtenerStockProducto(int numRef) {
    	Producto producto = em.find(Producto.class, numRef);
    	if (producto != null) {
    		return producto.getStock();
    	}
    	return 0;
    }
    public void asignarOrdenCliente(String usuarioCliente, int numeroOrden) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	Cliente cliente = em.find(Cliente.class, usuarioCliente);
    	OrdenDeCompra ord = em.find(OrdenDeCompra.class, numeroOrden);
    	cliente.agregarCompra(ord);
    	
    	em.getTransaction().commit();
    	em.close();
    }
    
    
    
    // CASO DE USO 5: VER INFORMACION DE CLIENTE 
    public List<DTCliente> listarClientes() {
    	List<Cliente> clientes = null;
    	
    	try {
    		clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	
    	List <DTCliente> dts = new ArrayList<>();
    	for (Cliente cli : clientes) {
    		dts.add(cli.crearDt());
    	}
    	return dts;
    }
    
    
    
    // CASO DE USO 6: VER INFORMACION DE PROVEEDOR
    public List<DTProveedor> listarProveedores(){
    	List<Proveedor> proveedores = null;
    	
    	try {
    		proveedores = em.createQuery("SELECT p FROM Proveedor p", Proveedor.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	if (proveedores == null) {
    		return null;
    	}
    	List <DTProveedor> dts = new ArrayList<>();
    	for (Proveedor prov : proveedores) {
    		dts.add(prov.crearDt());
    	}
    	return dts;
    }
    
    
    
    // CASO DE USO 7: CANCELAR ORDEN DE COMPRA
    public Cliente getClienteDeOrden(Integer orden) {
    	List <Cliente> usuarios = em.createQuery("SELECT c FROM Cliente", Cliente.class).getResultList();
        for (Cliente cliente : usuarios) {
        	System.out.println("Verificando cliente: " + cliente.getCorreo()); // Debug
        	if (cliente.existeOrden(orden)) {
        		System.out.println("Cliente encontrado con orden: " + orden); // Debug
        		return cliente;
        	}
        }
        System.out.println("No se encontró cliente con la orden: " + orden); // Debug
        return null;
    }

  

    
    public void eliminarOrdenDeCompra(int numero) throws OrdenDeCompraException {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
    	
    	OrdenDeCompra orden = em.find(OrdenDeCompra.class, numero);
        if (orden == null) {
            throw new OrdenDeCompraException("La orden de compra no existe");
        }
        Cliente cliente = getClienteDeOrden(orden.getNumero());
        if (cliente == null) {
        	throw new OrdenDeCompraException("No se encontró un cliente");        	
        }
        cliente.eliminarOrden(numero);
        Map<Integer, Item> items = orden.getItems();
        items.clear();
        em.remove(orden);
        
        em.getTransaction().commit();
        em.close();
    }
    public boolean existeOrden(int num) {
    	return em.find(OrdenDeCompra.class, num) != null;
    }
    
    
    
    // CASO DE USO 8: MODIFICAR DATOS DE PRODUCTO
    public void borrarProducto(int numero, String titulo) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
        
        Producto producto = em.find(Producto.class, numero);
        producto = em.merge(producto);
        em.remove(producto);
        em.getTransaction().commit();
        em.close();
    }


    
    // CASO DE USO 9: VER INFORMACION DE PRODUCTO
    public List<DtProducto> listarProductosPorCategoria(String cat) throws ProductoException {
    	List <DtProducto> listaProductos = new ArrayList<>();
    	Cat_Producto prodC = em.find(Cat_Producto.class, cat);
    	if (prodC.getProductos().isEmpty()) {
    		throw new ProductoException("Esta categoría no cuenta con productos");
    	}
    	for (Entry<Integer, Producto> entry: prodC.getProductos().entrySet()) {
    		Producto producto = entry.getValue();
    		listaProductos.add(producto.crearDT());
    	}	
    	return listaProductos;
    }
    
    
    public Producto getProdByCateogria(String cat, int numRef) throws ProductoException {
        Cat_Producto prodC = em.find(Cat_Producto.class, cat);
        
        if (prodC == null || prodC.getProductos().isEmpty()) {
            throw new ProductoException("Esta categoría no cuenta con productos");
        }
        
        Producto producto = prodC.getProducto(numRef);
        if (producto == null) {
            throw new ProductoException("El producto no existe en esta categoría");
        }

        return producto;
    }

    public List<DtProducto> listarALLProductos() throws ProductoException {
    	List<DtProducto> listaProductos = new ArrayList<>();
    	List<Producto> productos = null;
    	try {
    		productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    	} catch (Exception e) {
    		System.out.print(e);
    	}
    	
    	for (Producto prod: productos) {
    		DtProducto dtProducto = prod.crearDT();
    		listaProductos.add(dtProducto);
    	}
	
    	if (listaProductos.isEmpty()) {
    		throw new ProductoException("No se ha encontrado ningun producto para listar");
    	}
    	return listaProductos;
    }
    public DtProducto getDtProducto(int numRef) {
    	Producto producto = em.find(Producto.class, numRef);

    	if(producto != null) {
    		return producto.crearDT();
    	}
    	return null;
    }
    
    public Producto getProducto(int numRef) {
    	return em.find(Producto.class, numRef);
    }
    
    
    // CASO DE USO 10: VER INFORMACION DE ORDEN DE COMPRA
    public List<DTOrdenDeCompra> listarOrdenes() {
    	List <OrdenDeCompra> ords = em.createQuery("SELECT o FROM OrdenDeCompra o", OrdenDeCompra.class).getResultList();
    	List<DTOrdenDeCompra> lista = new ArrayList<>();
    	
    	for (OrdenDeCompra o : ords) {;
    		lista.add(o.crearDT());
    	}
    	
    	return lista;
    }
    public boolean existenOrdenesParaListar() {
    	return !this.listarOrdenes().isEmpty();
    }
    public boolean comprobarCat(String cat) throws CategoriaException {
    	if (em.find(Categoria.class, cat) == null) {
    		throw new CategoriaException("Esta categoria no existe");
    	}
    	return true;
    } 
    public void eliminarPDesdeProveedor(String proveedor, int numRef) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
         
    	Proveedor prov = em.find(Proveedor.class, proveedor);
    	prov.eliminarProd(numRef);
    	em.remove(em.find(Producto.class, numRef));

    	em.getTransaction().commit();
    	em.close();
    }
    
    

	 // MOSTRAR PERFIL CLIENTE
	 public DTCliente mostrarPerfilCliente(String nick) {
	 	Cliente cliente = em.find(Cliente.class, nick);
	 	
	 	return cliente.crearDt();
	 }
	 
	 // MOSTRAR PERFIL PROVEEDOR
	 public DTProveedor mostrarPerfilProveedor(String nick) {
		 Proveedor proveedor = em.find(Proveedor.class, nick);
		 
		 return proveedor.crearDt();
	 }
	 
	 // Traer Ordenes de compras de un cliente
	 public List<DTOrdenDeCompra> getOrdenesCliente(String nick) {
		 Cliente cliente = em.find(Cliente.class, nick);
		 em.refresh(cliente);
		 return cliente.mostrarCompras();
	 }
	 
	 
	 public Usuario getUserByEmail(String email) throws UsuarioException {
		 Usuario u = null;
		 try {
			 u = em.createQuery("SELECT u FROM Usuario WHERE u.correo='" + email + "'", Usuario.class).getSingleResult();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 if(u == null) {
			 throw new UsuarioException("No se ha encontrado al usuario");
		 }
		 return u;
	 }
	 
	
	
	 
	 public void agregarImagenesDesdeProveedor(String proveedor, int numRef, String imagen) {
		 Proveedor usr = (Proveedor) this.getUsuario(proveedor);
		 if (usr == null) {
			 return;
		 }
		 
		 Producto producto1 = usr.obtenerProd(numRef);
		 
		 if (producto1 == null) {
			 return;
		 }
		 
		 producto1.agregarImagen(imagen);
		 
	 }
	 /*
	  * Creo que este no se utiliza
	 public void agregarImagenesProducto(String cat, int num, String imagen) throws ProductoException {
	    	this.getProdByCateogria(cat, num).getImagenes().add(imagen);
	   }
	 */
	 public List<Categoria> getCategoriasLista() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		 EntityManager em = emf.createEntityManager();
	    	
		 List<Categoria> categorias = null;
		 try {
			 categorias = em.createQuery("SELECT c FROM Categoria", Categoria.class).getResultList();
		 } catch (Exception e) {
			 em.close();
			 System.out.print(e);
		 }
		 em.close();
		 return categorias;
	 }
	 
	 public List<Producto> buscarProductos(String query) {
		    List<Producto> resultados = new ArrayList<>();

		    if (query == null || query.trim().isEmpty()) {
		        return resultados; 
		    }

		    String queryLower = query.toLowerCase(); 
		    List <Producto> prods = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
		    for (Producto producto : prods) {
		    	if (producto.getNombre().toLowerCase().contains(queryLower) ||
		    			producto.getCategorias().stream().anyMatch(cat -> cat.getNombre().toLowerCase().contains(queryLower)) ||
		    			producto.getProveedor().getNick().toLowerCase().contains(queryLower)) {
		    		resultados.add(producto);
		    	}
		    }
		    return resultados; 
		}
	 
	 public List<Producto> getAllProductos() {
		 return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
	 }
	 
	 
	 public void realizarCompra(OrdenDeCompra orden, String nickCliente) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		 EntityManager em = emf.createEntityManager();
		 em.getTransaction().begin();
		 
		    Cliente cliente = em.find(Cliente.class, nickCliente);
		    System.out.println("Realizando compra para el cliente: " + nickCliente);
		    System.out.println("Número de orden: " + orden.getNumero());
		    
		 if (cliente == null) {
			 em.getTransaction().commit();
			 em.close();
			 System.out.println("Cliente no encontrado");
			 return; 
		 }
		 em.persist(orden);
		 System.out.println("Orden guardada: " + orden.getNumero());
		    
		 // Agregar compra al cliente
		 em.merge(cliente);
		 cliente.agregarCompra(orden);
		    
		 Map<Integer, Item> itemsAdquiridos = orden.getItems();
		 for (Map.Entry<Integer, Item> entry : itemsAdquiridos.entrySet()) {
			 Item item = entry.getValue();
			 int numeroRef = item.getProducto().getNumRef();
			 Producto producto = this.getProducto(numeroRef);
		        
			 if (producto != null) {
				 System.out.println("Actualizando stock para producto: " + numeroRef);
				 producto.setCantidadComprada(producto.getCantidadComprada() + item.getCant());
				 producto.setStock(producto.getStock() - item.getCant());
				 System.out.println("Nuevo stock para producto " + numeroRef + ": " + producto.getStock());
			 } else {
				 System.out.println("Producto no encontrado: " + numeroRef);
			 }
		 }
		 em.getTransaction().commit();
		 em.close();
	 }
	 
	 public void realizarCompraPRUEBA(int orden, String nickCliente) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		 EntityManager em = emf.createEntityManager();
		 em.getTransaction().begin();
		 OrdenDeCompra ord = em.find(OrdenDeCompra.class, orden);
		 OrdenDeCompra guardar = ord;
		 em.remove(ord);
		 
		 
		 em.persist(guardar);
		 
		 Cliente cliente = em.find(Cliente.class, nickCliente);
		 System.out.println("Realizando compra para el cliente: " + nickCliente);
		 System.out.println("Número de orden: " + ord.getNumero());
		    
		 if (cliente == null) {
			 System.out.println("Cliente no encontrado");
			 return; 
		 }

		 System.out.println("Orden guardada: " + ord.getNumero());
		    
		    // Agregar compra al cliente
		 cliente.agregarCompra(ord);
		    
		 em.merge(cliente);
		 em.merge(ord);
		    
		    
		 Map<Integer, Item> itemsAdquiridos = ord.getItems();
		 for (Map.Entry<Integer, Item> entry : itemsAdquiridos.entrySet()) {
			 Item item = entry.getValue();
			 int numeroRef = item.getProducto().getNumRef();
			 Producto producto = this.getProducto(numeroRef);
		        
			 if (producto != null) {
				 System.out.println("Actualizando stock para producto: " + numeroRef);
				 producto.setCantidadComprada(producto.getCantidadComprada() + item.getCant());
				 producto.setStock(producto.getStock() - item.getCant());
				 System.out.println("Nuevo stock para producto " + numeroRef + ": " + producto.getStock());
			 } else {
				 System.out.println("Producto no encontrado: " + numeroRef);
			 }
		 }
		 em.getTransaction().commit();
		 em.close();
	 }
	 
	 
	 	public List<Usuario> listaUsuarios(){
	 		return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
	 	}
	 	
	 	public DTOrdenDeCompra getOrden(int numero) {
	 		return em.find(OrdenDeCompra.class, numero).crearDT();
	 	}
	 	
	 public void cambiarEstadoOrden(String estado, String com, int numero, String cliente) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		 EntityManager em = emf.createEntityManager();
		 em.getTransaction().begin();
		 
		 Cliente client = em.find(Cliente.class, cliente);
	 		
	 		if (client != null) {
	 			DTEstado nuevoEstado = new DTEstado(estado, com);
	 			em.find(OrdenDeCompra.class, numero).setEstado(nuevoEstado);
	 			
	 			String recipientEmail = client.getCorreo();
	 	        //String recipientEmail = "maria.vairo@estudiantes.utec.edu.uy";
	 			System.out.println("Correo del cliente: " + recipientEmail);


	 	        try {
	 	            if (recipientEmail != null && !recipientEmail.isEmpty()) {
	 	                // Enviar el correo de bienvenida
	 	            	System.out.println("Estado: " + estado);
	 	            	System.out.println("recipientEmail: " + recipientEmail);
	 	                emailService.sendChangeState(recipientEmail, estado);
	 	                System.out.println("Correo de cambio de estado enviado a " + recipientEmail);
	 	            } else {
	 	                System.out.println("Cambio de estado Error: No se proporcionó una dirección de correo válida.");
	 	            }
	 	        } catch (Exception e) {
	 	        	em.getTransaction().commit();
	 		 		em.close();
	 	            System.out.println("Error al intentar enviar el correo de cambio de estado: " + e.toString());
	 	            e.printStackTrace();  // Imprime el rastro completo de la excepción en la consola
	 	            // e.printStackTrace();
	 	        }
	 	        em.getTransaction().commit();
		 		em.close();
	 			return;
	 		}
	 		
	 		System.out.print("no se pudo cambiar el estado");
	 		em.getTransaction().commit();
	 		em.close();
	 	}
	 
	 public void cambiarEstadoOrdenconDT(DTEstado est, int numero, String cliente) {
		 Cliente client = em.find(Cliente.class, cliente);
	 		
	 		if (client != null) {
	 			em.find(OrdenDeCompra.class, numero).setEstado(est);
	 			
	 			String recipientEmail = client.getCorreo();
	 	        //String recipientEmail = "maria.vairo@estudiantes.utec.edu.uy";
	 			System.out.println("Correo del cliente: " + recipientEmail);


	 	        try {
	 	            if (recipientEmail != null && !recipientEmail.isEmpty()) {
	 	                // Enviar el correo de bienvenida
	 	            	System.out.println("Estado: " + est.getEstado());
	 	            	System.out.println("recipientEmail: " + recipientEmail);
	 	                emailService.sendChangeState(recipientEmail, est.getEstado());
	 	                System.out.println("Correo de cambio de estado enviado a " + recipientEmail);
	 	            } else {
	 	                System.out.println("Cambio de estado Error: No se proporcionó una dirección de correo válida.");
	 	            }
	 	        } catch (Exception e) {
	 	            System.out.println("Error al intentar enviar el correo de cambio de estado: " + e.toString());
	 	            e.printStackTrace();  // Imprime el rastro completo de la excepción en la consola
	 	            // e.printStackTrace();
	 	        }
	 	        
	 			return;
	 		}
	 		
	 		System.out.print("no se pudo cambiar el estado");
	 }
	 	
	 // Caso de uso: alta reclamo
	 	
	 	 	public void agregarReclamo(String texto, LocalDateTime fecha, Producto p, Proveedor prov, Cliente autor) throws ReclamoException {
	 	 		
	 	 		
	 	 		Reclamo r = new Reclamo(texto, fecha, p, prov, autor);
	 	 		
	 	 		
	 	 		
	 	 		this.getProducto(p.getNumRef()).agregarReclamo(r);
	 	 	}
	 
	 
	 public void notificarComentaristas(Producto producto, String nuevoComentarioTexto, Cliente autorComentario) {
		    System.out.println("Iniciando notificación a los comentaristas.");

		    // Verificar que los parámetros no sean nulos
		    if (producto == null || nuevoComentarioTexto == null || autorComentario == null) {
		        System.out.println("Error: Parámetros inválidos. Asegúrate de que el producto, el comentario y el autor no sean nulos.");
		        return;
		    }

		    List<Comentario> comentarios = producto.getComentarios();
		    if (comentarios == null || comentarios.isEmpty()) {
		        System.out.println("No hay comentarios previos en el producto.");
		        return;
		    }

		    // Notificar a los comentaristas
		    for (Comentario comentario : comentarios) {
		        Cliente comentarista = comentario.getAutor();

		        // Enviar notificación solo si el comentarista no es el autor del nuevo comentario
		        if (!comentarista.getCorreo().equals(autorComentario.getCorreo())) {
		            String recipientEmail = comentarista.getCorreo();
		            try {
		                // Enviar la notificación
		            	System.out.print("SE ENVIO LA NOTIFICACIÓN");
		                emailService.sendCommentNotification(recipientEmail, producto.getNombre(), autorComentario.getNombre(), nuevoComentarioTexto);
		            } catch (Exception e) {
		                System.out.println("Error al intentar enviar la notificación a " + recipientEmail + ": " + e.getMessage());
		                e.printStackTrace();
		            }
		        }
		    }
		}

	 //public void agregarCliente(Cliente cliente) {
	  //      listaClientes.add(cliente);
	   // }

	    
	 public List<Cliente> obtenerClientesQueHanCompradoDelProveedor(Proveedor proveedor) {
	    List<Cliente> clientesQueHanComprado = new ArrayList<Cliente>();
	    
	    for (Cliente cliente : getAllClientes()) {
	        for (OrdenDeCompra orden : cliente.getOrdenes()) {
	            if (orden.getProveedor().equals(proveedor)) {
	                clientesQueHanComprado.add(cliente);
	                break;
	            }
	        }
	    }
	    
	    return clientesQueHanComprado;
	}
	 
	 
	 public void notificarClientesNuevoProducto(Producto nuevoProducto, Proveedor proveedor) {
	    System.out.println("Iniciando notificación de registro de nuevo producto a los clientes.");

	    // Verificar que los parámetros no sean nulos
	    if (nuevoProducto == null || proveedor == null) {
	        System.out.println("Error: Parámetros inválidos. Asegúrate de que el producto y el proveedor no sean nulos.");
	        return;
	    }

	    // Obtener la lista de clientes que han comprado productos del proveedor
	    List<Cliente> clientes = obtenerClientesQueHanCompradoDelProveedor(proveedor);

	    // Debug: Mostrar todos los clientes que han comprado al proveedor
	    if (clientes.isEmpty()) {
	        System.out.println("No hay clientes que hayan comprado productos de este proveedor.");
	    } else {
	        System.out.println("Clientes que han comprado al proveedor " + proveedor.getNombre() + ":");
	        for (Cliente cliente : clientes) {
	            System.out.println("- " + cliente.getNombre() + " (" + cliente.getCorreo() + ")");
	        }
	    }

	    // Notificar a cada cliente
	    for (Cliente cliente : clientes) {
	        String recipientEmail = cliente.getCorreo();
	        
	        // Verificar si el cliente tiene un correo válido
	        if (recipientEmail == null || recipientEmail.isEmpty()) {
	            System.out.println("El cliente " + cliente.getNombre() + " no tiene un correo registrado para notificaciones.");
	            continue;
	        }

	        try {
	            // Llamada al servicio de email para enviar la notificación del nuevo producto
	            emailService.sendNewProductNotification(
	                recipientEmail,
	                proveedor.getNombre(),
	                cliente.getNombre(),
	                nuevoProducto.getNombre()
	            );
	            System.out.println("Notificación enviada a " + cliente.getNombre());
	        } catch (Exception e) {
	            System.out.println("Error al enviar la notificación a " + cliente.getNombre() + ": " + e.getMessage());
	        }
	    }
	}

	 
	 public List<Cliente> getAllClientes() {
		 return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
	 }

	 public Icon resizeIcon(ImageIcon icon, int width, int height) {
	        Image img = icon.getImage();
	        Image resizedImage = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
	        return new ImageIcon(resizedImage);
	    }
	 
	 // FILTRO PRODUCTOS DESTACADOS
	 public List<Producto> obtenerProductosDestacados() {
		 	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	        EntityManager em = emf.createEntityManager();
	        
	        try {
	            // Consulta JPQL para obtener productos ordenados por 'comprasUnicas' de mayor a menor
	            String jpql = "SELECT p FROM Producto p ORDER BY p.cantidadUnicaComprada DESC";
	            Query query = em.createQuery(jpql, Producto.class);
	            
	            
	            query.setMaxResults(10);
	            
	           
	            List<Producto> productosDestacados = query.getResultList();
	            
	            return productosDestacados;
	        } finally {
	            em.close(); 
	            emf.close();
	        }
	        
	    }
	 
	 
	 // PROBANDO
	 
	 public Integer iniciarOrdenVacia(String nickProveedor) {
		 Proveedor prov = em.find(Proveedor.class, nickProveedor);
		 
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	        EntityManager em = emf.createEntityManager();
		 
		 if(prov == null) {
			 System.out.print("NO se encontro prov");
			 
			 return 0;
		 }
		 
		 
		 em.getTransaction().begin();
		 OrdenDeCompra ord = new OrdenDeCompra(prov);
		 em.persist(ord);
		 
		 em.getTransaction().commit();
		 
		 System.out.print("SE AGREGO LA COMPRA");
		 
		 em.close();
		 emf.close();
		 
		 return ord.getNumero();
	 
	 }
	 
	 public void agregarItemsAOrden(int numeroOrden, int numProducto, int cantidad) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        
	        OrdenDeCompra ord = em.find(OrdenDeCompra.class, numeroOrden);
	        
	        ord.agregarItem(em.find(Producto.class, numProducto), cantidad);
	        
	        em.merge(ord);
	        
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        	
	 }
	 
	 
	 public void agregarComentario(int numRef, int comentarioId, String mensaje, String nickCliente) throws ProductoException {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	     EntityManager em = emf.createEntityManager();
	     em.getTransaction().begin();
	     
	     
	     Producto producto = em.find(Producto.class, numRef);
	     Cliente cl = em.find(Cliente.class, nickCliente);
	     Comentario nuevoComentario = new Comentario(comentarioId, mensaje, cl, LocalDateTime.now());
	     
	     cl.agregarComentario(nuevoComentario, producto.getNombre());
	     producto.agregarComentario(nuevoComentario);
	     
	     
	     em.persist(nuevoComentario);
	     
	     em.merge(cl);
	     em.merge(producto);
	     em.getTransaction().commit();
	     
	     em.close();
	     emf.close();
	 }
	 
	 
}