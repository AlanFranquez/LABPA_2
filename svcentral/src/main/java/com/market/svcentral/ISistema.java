package com.market.svcentral;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.market.svcentral.exceptions.CategoriaException;
import com.market.svcentral.exceptions.OrdenDeCompraException;
import com.market.svcentral.exceptions.ProductoException;
import com.market.svcentral.exceptions.ReclamoException;
import com.market.svcentral.exceptions.UsuarioException;
import com.market.svcentral.exceptions.UsuarioRepetidoException;

public interface ISistema {
    public abstract boolean validarCorreo(String correo);
    
    public void agregarProveedor(String nick, String correo, String nombre, String apellido, DTFecha fechaNacimiento, String compania, String link, String contra, String confContra) throws UsuarioRepetidoException;
    
    public void agregarCliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contra, String confContra) throws UsuarioRepetidoException;
    
    public abstract void agregarImagenUsuario(String nick, String image);
    
    public abstract Usuario getUsuario(String nickname);
    
    public abstract List<DTCliente> listarClientes();
    
    public abstract boolean existeCategoria(String nombre);
    
    public abstract List <String> listarSoloNombresPadresCat();
    
    public abstract void agregarCategoria(String nombre) throws CategoriaException;
    
    public abstract void agregarCategoriaConProductos(String nombre) throws CategoriaException;
    
    public abstract void asignarlePadreCategoria(String nombrePadre, String nombre) throws CategoriaException;
        
    public abstract List<DTOrdenDeCompra> listarOrdenes();
    
    public Categoria getCat(String nombre);
    
    public Categoria[] getCategorias();
    
    public Map<String, Categoria> getCategoriasLista();
    
    public abstract boolean existenOrdenesParaListar();

	public abstract DefaultMutableTreeNode arbolCategorias();

	public abstract DefaultMutableTreeNode arbolProductos();

	public abstract List<DTProveedor> listarProveedores();

    public abstract void addOrdenes(OrdenDeCompra orden, String nickUsuario);

	public abstract void agregarProductoCategoria(String catName, int numRef) throws CategoriaException;

	public abstract boolean esPadre(String catName);

	public abstract void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones,
			float precio, String proveedor, int stock);

	public abstract void CrearOrden();
	
	public abstract List<DtProducto> listarALLProductos() throws ProductoException;
	
	public abstract List<DtProducto> listarProductosPorCategoria(String cat) throws ProductoException;
	
	public boolean comprobarCat(String cat) throws CategoriaException;

	public abstract void asignarOrdenCliente(String cliente);

	public abstract DtProducto getDtProducto(int numRef);

	public abstract void eliminarOrdenDeCompra(int numero) throws OrdenDeCompraException;

	public abstract boolean existeOrden(int num);
	
	public abstract void eliminarUltimaOrden();
	
	public abstract boolean verificarUnicidadProducto(String nombreCategoria, int numRef, String titulo);
	
	public abstract void eliminarPDesdeProveedor(String proveedor, int numRef);
	
	public abstract void borrarProducto(int numRef, String titulo);

	public abstract Integer obtenerStockProducto(int numRef);
	
	 // MOSTRAR PERFIL CLIENTE
	 public DTCliente mostrarPerfilCliente(String nick);
	 
	 // MOSTRAR PERFIL PROVEEDOR
	 public DTProveedor mostrarPerfilProveedor(String nick);
	 
	 // Traer Ordenes de compras de un cliente
	 public List<DTOrdenDeCompra> getOrdenesCliente(String nick);
	 
	 
	 public Usuario getUserByEmail(String email) throws UsuarioException;
	 
	 
	 public void agregarImagenesDesdeProveedor(String proveedor, int numRef, String imagen);
	 
	 public Producto getProdByCateogria(String cat, int numRef) throws ProductoException;
	   

	  public Producto getProducto(int numRef);
	   
	  public List<Producto> buscarProductos(String query);
	   
	  public List<Producto> getAllProductos();
	  
	  public void realizarCompra(OrdenDeCompra orden, String nickCliente);
	  
	  public List<Usuario> listaUsuarios();
	  
	  public OrdenDeCompra getOrden(int numero);
	  
	  public void cambiarEstadoOrden(String estado, String com, int numero, String cliente);
	  
	  public void notificarComentaristas(Producto producto, String nuevoComentarioTexto, Cliente autorComentario);
	  
	  //public void agregarCliente(Cliente cliente);
	  
	  List<Cliente> obtenerClientesQueHanCompradoDelProveedor(Proveedor proveedor);
	  
	  public void notificarClientesNuevoProducto(Producto nuevoProducto, Proveedor proveedor);

	  public void agregarReclamo(String texto, LocalDateTime fecha, Producto p, Proveedor prov, Cliente autor) throws ReclamoException;
}