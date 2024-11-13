package com.market.svcentral;

import java.awt.Image;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.market.svcentral.exceptions.CategoriaException;
import com.market.svcentral.exceptions.OrdenDeCompraException;
import com.market.svcentral.exceptions.ProductoException;
import com.market.svcentral.exceptions.UsuarioException;
import com.market.svcentral.exceptions.UsuarioRepetidoException;

public class Sistema implements ISistema {
    private static Sistema instance = null;
    private Map<String, Usuario> usuarios;
    private Map<String, Categoria> categorias;
    private Map<Integer, OrdenDeCompra> ordenes;
    private Map<String, Categoria> arbolCategorias;
    private final EmailService emailService = new EmailService(this);
    private int contadorComentarios = 0;
    //private List<Cliente> listaClientes;

    private Sistema() {
        // Inicialización de colecciones
        this.usuarios = new HashMap<>();
        this.categorias = new HashMap<>();
        this.ordenes = new HashMap<>();
        //this.listaClientes = new ArrayList<>();
        this.arbolCategorias = new HashMap<>();
    }

    public static synchronized Sistema getInstance() {
        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
    }

    // CASO DE USO 1: REGISTRAR USUARIO
	public boolean verificarUnicidad(String nick, String correo) {
    	Usuario usuario = this.usuarios.get(nick);
        if (usuario != null) {
        	return false;
        }
        for (Usuario user : this.usuarios.values()) {
            if (user.getCorreo() != null && user.getCorreo().equals(correo)) {
                return false;
            }
        }
        return true;
        
    }
    public void agregarProveedor(String nick, String correo, String nombre, String apellido, DTFecha fechaNacimiento, String compania, String link, String contra, String confContra) throws UsuarioRepetidoException {
    	if (!verificarUnicidad(nick, correo)) {
    		throw new UsuarioRepetidoException("Ya existe un usuario con nick: " + nick + " orden email: " + correo);
    	}
    	if (!contra.equals(confContra)) {
    		throw new UsuarioRepetidoException("Contraseñas Diferentes");
    	}	
    	Proveedor nuevoProveedor = new Proveedor(nombre, nick, apellido, correo, fechaNacimiento, compania, link, contra);
    	usuarios.put(nick, nuevoProveedor);
    }
    public void agregarCliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contra, String confContra) throws UsuarioRepetidoException {
    	if (!verificarUnicidad(nick, correo)) {
    		throw new UsuarioRepetidoException("Ya existe un usuario con nick: " + nick + " orden email: " + correo);
    	}
    	if (!contra.equals(confContra)) {
    		throw new UsuarioRepetidoException("Contraseñas Diferentes");
    	}

    	Cliente nuevoCliente = new Cliente(nombre, nick, apellido, correo, fecha, contra);
    	usuarios.put(nick, nuevoCliente);
    }
    public void agregarImagenUsuario(String nick, String imagen) {
    	Usuario usuarioBuscado = this.usuarios.get(nick);
    	if (usuarioBuscado == null) {
    		System.out.println("Usuario con nick: " + nick + " no encontrado.");
    		return;
    	}
    	usuarioBuscado.setImagen(imagen);
    }
    public Usuario getUsuario(String nickname) {
    	return this.usuarios.get(nickname);
    }
    public boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    
    
    // CASO DE USO 2: REGISTRAR PRODUCTO
    public boolean verificarUnicidadProducto(String nombreCategoria, int numRef, String titulo) {
    	Cat_Producto cat = (Cat_Producto) this.categorias.get(nombreCategoria);
    	if (!cat.verificarProducto(numRef, titulo)) {
    		return false;
    	}
    	return true;
    }
    public void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones, float precio, String prov, int stock) {    	
    	Proveedor proveedor = (Proveedor) usuarios.get(prov);
        Producto producto = new Producto(titulo, descripcion, precio, numRef, especificaciones, proveedor, stock);
        proveedor.agregarProd(producto);
    }
    public DefaultMutableTreeNode arbolCategorias() {
      	 DefaultMutableTreeNode root = new DefaultMutableTreeNode("Cats");
      	 for (Categoria cat : arbolCategorias.values()) {
      		 DefaultMutableTreeNode child = arbolCategorias(cat);
      		 root.add(child);
      	 }
      	 return root;
    }
    public DefaultMutableTreeNode arbolCategorias(Categoria cat) {
     	 	DefaultMutableTreeNode rama = new DefaultMutableTreeNode(cat.getNombre());
     	 	if (cat.getTipo() == "Padre") {
     	 		Map<String, Categoria> hijos = ((Cat_Padre) cat).getHijos();
     	 		if (hijos.size() >= 1) {
     	 			for (Categoria hijo : hijos.values()) {
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
    	Categoria cat = categorias.get(nombre);
    	return (cat instanceof Cat_Padre);
    }
    public void agregarProductoCategoria(String catName, int numRef) throws CategoriaException {
    	for (Usuario user : usuarios.values()) {
    		if (user instanceof Proveedor) {
    			Proveedor proveedor = (Proveedor) user;
    			Producto producto = proveedor.obtenerProd(numRef);
    			if (producto != null) {
    				Cat_Producto cat = (Cat_Producto) categorias.get(catName);
    				if (cat == null) {
    					throw new CategoriaException("Hubo un error en la Categoria, vuelva a intentarlo");
    				}
    				producto.agregarCategorias(cat);
    				cat.agregarProducto(producto);
    			}
    		}
    	}
    }
    
    
    public Categoria getCat(String nombre) {
    	return this.categorias.get(nombre);
    }
    
    public Categoria[] getCategorias() {
        Collection<Categoria> collection = this.categorias.values(); // Obtiene todas las categorías
        return collection.toArray(new Categoria[collection.size()]); // Convierte a un arreglo
    }
    
    // CASO DE USO 3: ALTA DE CATEGORIA
    public void agregarCategoria(String nombre) throws CategoriaException {
	   if (existeCategoria(nombre)) {
		   throw new CategoriaException("El nombre de la categoria ya existe");
	   }
	   Cat_Padre nuevaCategoria = new Cat_Padre(nombre);
	   this.categorias.put(nombre, nuevaCategoria);
	   this.arbolCategorias.put(nombre, nuevaCategoria);
    }
    public void agregarCategoriaConProductos(String nombre) throws CategoriaException{
	   if (existeCategoria(nombre)) {
		   throw new CategoriaException("Esta categoria ya existe");
	   }
	   Cat_Producto nuevaCategoria = new Cat_Producto(nombre);
	   this.categorias.put(nombre, nuevaCategoria);
	   this.arbolCategorias.put(nombre, nuevaCategoria);
   }
    public void asignarlePadreCategoria(String nombrePadre, String nombre) throws CategoriaException {
	   if (nombre == nombrePadre) {
		   throw new CategoriaException("Una categoría no puede ser su propio padre");
	   }
	   
	   Cat_Padre catPadre = (Cat_Padre) this.categorias.get(nombrePadre);
	   Categoria cat = this.categorias.get(nombre);

	   if (catPadre.verificarSiYaEsHijo(nombre)) {
		   throw new CategoriaException("Esta categoria ya es su hijo");
	   }
	   
	   cat.setPadre(catPadre);
	   catPadre.agregarHijo(cat);
	   arbolCategorias.remove(cat.getNombre());
   }

    public List <String> listarSoloNombresPadresCat() {
    	List <String> listarPadres = new ArrayList<>();
    	for (Map.Entry<String, Categoria> entry : categorias.entrySet()) {
    		Categoria cat = entry.getValue();
    		if (cat.getTipo() == "Padre") {
    			Cat_Padre catPadre = (Cat_Padre) cat;
    			listarPadres.add(catPadre.getNombre());
    		}
    	}
    	return listarPadres;
    }
    public boolean existeCategoria(String nombre) {
        return this.categorias.containsKey(nombre);
    }
    
    
    
    // CASO DE USO 4: GENERAR ORDEN DE COMPRA
    public DefaultMutableTreeNode arbolProductos() {
   	 DefaultMutableTreeNode root = new DefaultMutableTreeNode("Cats");
   	 for (Categoria cat : arbolCategorias.values()) {
   		 DefaultMutableTreeNode child = arbolProductos(cat);
   		 root.add(child);
   	 }
   	return root;
    }
    public DefaultMutableTreeNode arbolProductos(Categoria cat) {
  	 	DefaultMutableTreeNode rama = new DefaultMutableTreeNode(cat.getNombre());
  	 	if (cat.getTipo() == "Padre") {
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
  	 		Map<Integer, Producto> productos = ((Cat_Producto) cat).getProductos();
  	 		if (productos.size() >= 1) {
  	 			for (Producto producto : productos.values()) {
  	 				DefaultMutableTreeNode child = new DefaultMutableTreeNode(producto.getNombre() + " - " + producto.getNumRef());
  					rama.add(child);
  	 			}
  	 		} else {
  	 			rama.add(new DefaultMutableTreeNode("Sin Elementos"));
  	 		}
  	 	}
  	return rama;
  }
    public void CrearOrden() {
    	int maxKey = ordenes.keySet().stream().max(Integer::compare).orElse(0);
    	OrdenDeCompra orden = new OrdenDeCompra(maxKey + 1);
    	ordenes.put(orden.getNumero(), orden);
    }
    public Integer obtenerStockProducto(int numRef) {
    	for (Usuario user : usuarios.values()) {
    		if (user instanceof Proveedor) {
    			Proveedor proveedor = (Proveedor) user;
    			Producto producto = proveedor.obtenerProd(numRef);
    			if (producto != null) {
    				return producto.getStock();
    			}
    		}
    	}
    	return 0;
    }
    public void eliminarUltimaOrden() {
    	int keyOrden = ordenes.size();
    	ordenes.remove(keyOrden);
    }
    public void asignarOrdenCliente(String usuarioCliente) {
    	Cliente cliente = (Cliente) usuarios.get(usuarioCliente);
    	cliente.agregarCompra(ordenes.get(ordenes.size()));
    }
    
    
    
    // CASO DE USO 5: VER INFORMACION DE CLIENTE 
    public List<DTCliente> listarClientes() {
        List<DTCliente> listaClientes = new ArrayList<>();
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            Usuario usuario = entry.getValue();
            if (usuario.getTipo().equals("cliente")) {
                Cliente usuarioCliente = (Cliente) usuario;
                listaClientes.add(usuarioCliente.crearDt());
            }
        }
        return listaClientes;
    }
    
    
    
    // CASO DE USO 6: VER INFORMACION DE PROVEEDOR
    public List<DTProveedor> listarProveedores(){
    	List<DTProveedor> listaProveedor = new ArrayList<>();
    	for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
    		Usuario usuario = entry.getValue();
    		if (usuario.getTipo().equals("proveedor")) {
    			Proveedor usuarioProveedor = (Proveedor) usuario;
    			listaProveedor.add(usuarioProveedor.crearDt());
    		}
    	}
    	return listaProveedor;
    }
    
    
    
    // CASO DE USO 7: CANCELAR ORDEN DE COMPRA
    public Cliente getClienteDeOrden(Integer orden) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                System.out.println("Verificando cliente: " + cliente.getCorreo()); // Debug
                if (cliente.existeOrden(orden)) {
                    System.out.println("Cliente encontrado con orden: " + orden); // Debug
                    return cliente;
                }
            }
        }
        System.out.println("No se encontró cliente con la orden: " + orden); // Debug
        return null;
    }

  

    
    public void eliminarOrdenDeCompra(int numero) throws OrdenDeCompraException {
    	OrdenDeCompra orden = this.ordenes.get(numero);
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
        this.ordenes.remove(numero);
        
    }
    public boolean existeOrden(int num) {
    	return ordenes.containsKey(num);
    }
    
    
    
    // CASO DE USO 8: MODIFICAR DATOS DE PRODUCTO
    public void borrarProducto(int numero, String titulo) {
    	Map<String, Categoria> cats = categorias;
    	Iterator<Map.Entry<String, Categoria>> iterator = cats.entrySet().iterator();

    	while (iterator.hasNext()) {
    	    Map.Entry<String, Categoria> entry = iterator.next();
    	    Categoria categoria = entry.getValue();

    	    // Verificar si la categoría es una instancia de Cat_Producto
    	    if (categoria instanceof Cat_Producto) {
    	        Cat_Producto prodC = (Cat_Producto) categoria;

    	        Iterator<Map.Entry<Integer, Producto>> prodIterator = prodC.getProductos().entrySet().iterator();

    	        while (prodIterator.hasNext()) {
    	            Map.Entry<Integer, Producto> prodEntry = prodIterator.next();
    	            Producto producto = prodEntry.getValue();

    	            // Comparar el título del producto usando equals y el número de referencia
    	            if (producto.getNombre().equals(titulo) && producto.getNumRef() == numero) {
    	                // Remover el producto del mapa
    	                prodIterator.remove();
    	            }
    	        }
    	    }
    	}
    }


    
    // CASO DE USO 9: VER INFORMACION DE PRODUCTO
    public List<DtProducto> listarProductosPorCategoria(String cat) throws ProductoException {
    	List <DtProducto> listaProductos = new ArrayList<>();
    	Cat_Producto prodC = (Cat_Producto) this.categorias.get(cat);
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
        Cat_Producto prodC = (Cat_Producto) this.categorias.get(cat);
        
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
    	List<Integer> numRefs = new ArrayList<Integer>();
    	
    	for (Map.Entry<String, Categoria> entry : this.categorias.entrySet()) {
    		Categoria cat = entry.getValue();
    		
    		if (cat.getTipo() == "Producto") {
    			Cat_Producto cProd = (Cat_Producto) cat;
    			List<DtProducto> listaPerProducto = cProd.listarProductos();
    			
    			if (listaPerProducto.isEmpty()) {
    				continue;
    			}
    			
    			for (DtProducto dtProducto: listaPerProducto) {
    				if (!numRefs.contains(dtProducto.getNumRef())) {
                		numRefs.add(dtProducto.getNumRef());
                		listaProductos.add(dtProducto);
                	}
    			}
    		}
    	}
    	if (listaProductos.isEmpty()) {
    		throw new ProductoException("No se ha encontrado ningun producto para listar");
    	}
    	return listaProductos;
    }
    public DtProducto getDtProducto(int numRef) {
    	for (Usuario user : usuarios.values()) {
    		if (user instanceof Proveedor) {
    			Proveedor proveedor = (Proveedor) user;
    			Producto producto = proveedor.obtenerProd(numRef);
    			if (producto != null) {
    				return producto.crearDT();
    			}
    		}
    	}
    	return null;
    }
    
    public Producto getProducto(int numRef) {
    	for (Usuario user : usuarios.values()) {
    		if (user instanceof Proveedor) {
    			Proveedor proveedor = (Proveedor) user;
    			Producto producto = proveedor.obtenerProd(numRef);
    			if (producto != null) {
    				return producto;
    			}
    		}
    	}
    	return null;
    }
    
    
    // CASO DE USO 10: VER INFORMACION DE ORDEN DE COMPRA
    public boolean existenOrdenesParaListar() {
        return !this.ordenes.isEmpty();
    }
    public List<DTOrdenDeCompra> listarOrdenes() {
    	List<DTOrdenDeCompra> lista = new ArrayList<>();
    	
    	for (Map.Entry<Integer, OrdenDeCompra> entry : this.ordenes.entrySet()) {
    		OrdenDeCompra orden = entry.getValue();
    		
    		lista.add(orden.crearDT());
    	}
    	
    	return lista;
    }
    /*
     * Creo que esto no se usa
    public List<DTCliente> obtenerSoloClientes() {
        List<DTCliente> listaClientes = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Cliente) {
            	Cliente cliente = (Cliente) usuario;
                listaClientes.add(cliente.crearDt());
            }
        }
        return listaClientes;
    }
    */
    public void addOrdenes(OrdenDeCompra orden, String nickUsuario) {
    	Usuario user = this.usuarios.get(nickUsuario);
    	
    	Cliente cliente = (Cliente) user;
    	ordenes.put(orden.getNumero(), orden);
    	cliente.agregarCompra(orden);
    }
    public boolean comprobarCat(String cat) throws CategoriaException {
    	if ((Cat_Producto) this.categorias.get(cat) == null) {
    		throw new CategoriaException("Esta categoria no existe");
    	}
    	return true;
    } 
    public void eliminarPDesdeProveedor(String proveedor, int numRef) {
    	Proveedor prov = (Proveedor) this.usuarios.get(proveedor);
    	prov.eliminarProd(numRef);
    }
    
    

	 // MOSTRAR PERFIL CLIENTE
	 public DTCliente mostrarPerfilCliente(String nick) {
	 	Cliente cliente = (Cliente) this.usuarios.get(nick);
	 	
	 	return cliente.crearDt();
	 }
	 
	 // MOSTRAR PERFIL PROVEEDOR
	 public DTProveedor mostrarPerfilProveedor(String nick) {
		 Proveedor proveedor = (Proveedor) this.usuarios.get(nick);
		 
		 return proveedor.crearDt();
	 }
	 
	 // Traer Ordenes de compras de un cliente
	 public List<DTOrdenDeCompra> getOrdenesCliente(String nick) {
		 Cliente cliente = (Cliente) this.usuarios.get(nick);
		 
		 return cliente.mostrarCompras();
	 }
	 
	 
	 public Usuario getUserByEmail(String email) throws UsuarioException {
		 for (Usuario usuario : usuarios.values()) {
			 	if (usuario.getCorreo() == email) {
			 		return usuario;
			 	}
	     }
		 
		 throw new UsuarioException("No se ha encontrado al usuario");
		 
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
	 public Map<String, Categoria> getCategoriasLista() {
		 return this.categorias;
	 }
	 
	 public List<Producto> buscarProductos(String query) {
		    List<Producto> resultados = new ArrayList<>();

		    if (query == null || query.trim().isEmpty()) {
		        return resultados; 
		    }

		    String queryLower = query.toLowerCase(); 

		    for (Categoria categoria : categorias.values()) {
		        if (categoria instanceof Cat_Producto) {
		            Cat_Producto catprod = (Cat_Producto) categoria;
		            for (Producto producto : catprod.getProductos().values()) {
		                if (producto.getNombre().toLowerCase().contains(queryLower) ||
		                    categoria.getNombre().toLowerCase().contains(queryLower) ||
		                    producto.getProveedor().getNick().toLowerCase().contains(queryLower)) {
		                    resultados.add(producto);
		                }
		            }
		        }
		    }
		    return resultados; 
		}
	 
	 public List<Producto> getAllProductos() {
		    List<Producto> resultados = new ArrayList<>();
		    for (Categoria categoria : categorias.values()) {
		        if (categoria instanceof Cat_Producto) {
		        	Cat_Producto catprod = (Cat_Producto) categoria;
		            for (Producto producto : catprod.getProductos().values()) {
		                resultados.add(producto);
		            }
		        }
		    }
		    return resultados;
		}
	 
	 
	 public void realizarCompra(OrdenDeCompra orden, String nickCliente) {
		    Cliente cliente = (Cliente) this.usuarios.get(nickCliente);
		    System.out.println("Realizando compra para el cliente: " + nickCliente);
		    System.out.println("Número de orden: " + orden.getNumero());
		    
		    if (cliente == null) {
		        System.out.println("Cliente no encontrado");
		        return; 
		    }

		    this.ordenes.put(orden.getNumero(), orden);
		    System.out.println("Orden guardada: " + orden.getNumero());
		    
		    // Agregar compra al cliente
		    cliente.agregarCompra(this.ordenes.get(orden.getNumero()));
		    
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
		}
	 
	 
	 	public List<Usuario> listaUsuarios(){
	 		Map<String, Usuario> personas = this.usuarios;
	 		List<Usuario> nuevaLista = new ArrayList<Usuario>();
	 		for (Map.Entry<String, Usuario> entry : personas.entrySet()) { 
	 			Usuario usuario = entry.getValue();
	 			
	 			nuevaLista.add(usuario);
	 			
	 		}
	 		
	 		
	 		return nuevaLista;
	 	}
	 	
	 	public OrdenDeCompra getOrden(int numero) {
	 		return this.ordenes.get(numero);
	 	}
	 	
	 public void cambiarEstadoOrden(String estado, String com, int numero, String cliente) {
	 		Cliente client = (Cliente) this.usuarios.get(cliente);
	 		
	 		if (client != null) {
	 			this.ordenes.get(numero).setEstado(estado, com);
	 			
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
	 	            System.out.println("Error al intentar enviar el correo de cambio de estado: " + e.toString());
	 	            e.printStackTrace();  // Imprime el rastro completo de la excepción en la consola
	 	            // e.printStackTrace();
	 	        }
	 	        
	 			return;
	 		}
	 		
	 		System.out.print("no se pudo cambiar el estado");
	 	}
	 
	 
	 public void notificarComentario(Producto producto, Comentario nuevoComentario, Comentario comentarioRespondido) {
		    if (producto == null || nuevoComentario == null) {
		        System.out.println("Error: El producto o el comentario nuevo son nulos.");
		        return;
		    }

		    // Obtener la lista de comentarios del producto
		    List<Comentario> comentarios = producto.getComentarios();

		    if (comentarios != null && !comentarios.isEmpty()) {
		        for (Comentario comentario : comentarios) {
		            Cliente comentarista = comentario.getAutor();
		            Cliente autorNuevoComentario = nuevoComentario.getAutor();

		            // Notificar a todos los comentaristas excepto al autor del nuevo comentario
		            if (!comentarista.getCorreo().equals(autorNuevoComentario.getCorreo())) {
		                String recipientEmail = comentarista.getCorreo();

		                try {
		                    emailService.sendCommentNotification(
		                        recipientEmail,
		                        producto.getNombre(),
		                        autorNuevoComentario.getNombre(),
		                        nuevoComentario.getTexto()
		                    );
		                    System.out.println("Notificación enviada a " + recipientEmail);
		                } catch (Exception e) {
		                    System.out.println("Error al enviar notificación a " + recipientEmail + ": " + e.getMessage());
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		    // Notificar al autor del comentario respondido (si aplica)
		    if (comentarioRespondido != null) {
		        Cliente autorComentarioOriginal = comentarioRespondido.getAutor();
		        Cliente autorNuevoComentario = nuevoComentario.getAutor();

		        // Verificar que el autor del comentario original no sea el mismo que el autor del nuevo comentario
		        if (!autorComentarioOriginal.getCorreo().equals(autorNuevoComentario.getCorreo())) {
		            try {
		                // Aquí usamos sendReplyNotification en lugar de sendCommentNotification
		                emailService.sendReplyNotification(
		                    autorComentarioOriginal.getCorreo(),
		                    producto.getNombre(),
		                    autorNuevoComentario.getNombre(),
		                    nuevoComentario.getTexto()
		                );
		                System.out.println("Notificación de respuesta enviada a " + autorComentarioOriginal.getCorreo());
		            } catch (Exception e) {
		                System.out.println("Error al enviar la notificación de respuesta: " + e.getMessage());
		                e.printStackTrace();
		            }
		        }
		    }
		}


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
		 List<Cliente> clientes = new ArrayList<Cliente>();
		 for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
			 Usuario usuario = entry.getValue();
			 if (usuario.getTipo().equals("cliente")) {
				 Cliente cliente = (Cliente) usuario;
				 clientes.add(cliente);
			 }
		 }
		 return clientes;
	 }
	 
	 public Cliente getClientePorCorreo(String email) {
	    for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
	        Usuario usuario = entry.getValue();
	        if (usuario instanceof Cliente && usuario.getCorreo().equalsIgnoreCase(email)) {
	            return (Cliente) usuario;
	        }
	    }
	    return null; // Retorna null si no se encuentra un cliente con el correo
	}
	 
	 public Cliente getClientePorToken(String token) {
	    for (Usuario usuario : usuarios.values()) {
	        // Verifica que sea un Cliente y que el token coincida
	        if (usuario instanceof Cliente) {
	            Cliente cliente = (Cliente) usuario;
	            if (token.equals(cliente.getTokenDesactivacion())) {
	                return cliente;
	            }
	        }
	    }
	    return null; // Retorna null si no encuentra un cliente con el token especificado
	}

	 public Cliente getClientePorNick(String nick) {
		    for (Usuario usuario : usuarios.values()) {
		        if (usuario instanceof Cliente && usuario.getNick().equalsIgnoreCase(nick)) {
		            return (Cliente) usuario;
		        }
		    }
		    return null;
		}

	 @Override
	 public synchronized int incrementarContadorComentarios() {
	     return ++contadorComentarios;
	 }
}