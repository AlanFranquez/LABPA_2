package com.market.svcentral;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
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
    
    public abstract void agregarProveedor(String nick, String correo, String nombre, String apellido, DTFecha fechaNacimiento, String compania, String link, String contra, String confContra) throws UsuarioRepetidoException;
    
    public abstract void agregarCliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contra, String confContra) throws UsuarioRepetidoException;
    
    public abstract void agregarImagenUsuario(String nick, String image);
    
    public abstract Usuario getUsuario(String nickname);
    
    public abstract List<DTCliente> listarClientes();
    
    public abstract boolean existeCategoria(String nombre);
    
    public abstract List <String> listarSoloNombresPadresCat();
    
    public abstract void agregarCategoria(String nombre) throws CategoriaException;
    
    public abstract void agregarCategoriaConProductos(String nombre) throws CategoriaException;
    
    public abstract void asignarlePadreCategoria(String nombrePadre, String nombre) throws CategoriaException;
        
    public abstract List<DTOrdenDeCompra> listarOrdenes();
    
    public abstract Categoria getCat(String nombre);
    
    public abstract Categoria[] getCategorias();
    
    public abstract List<Categoria> getCategoriasLista();
    
    public abstract boolean existenOrdenesParaListar();

	public abstract DefaultMutableTreeNode arbolCategorias();

	public abstract DefaultMutableTreeNode arbolProductos();

	public abstract List<DTProveedor> listarProveedores();

	public abstract void agregarProductoCategoria(String catName, int numRef) throws CategoriaException;

	public abstract boolean esPadre(String catName);

	public abstract void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones,
			float precio, String proveedor, int stock);

	public abstract void CrearOrden();
	
	public abstract List<DtProducto> listarALLProductos() throws ProductoException;
	
	public abstract List<DtProducto> listarProductosPorCategoria(String cat) throws ProductoException;
	
	public abstract boolean comprobarCat(String cat) throws CategoriaException;

	public abstract void asignarOrdenCliente(String cliente, int numeroOrden);

	public abstract DtProducto getDtProducto(int numRef);

	public abstract void eliminarOrdenDeCompra(int numero) throws OrdenDeCompraException;

	public abstract boolean existeOrden(int num);
	
	public abstract boolean verificarUnicidadProducto(int numRef, String titulo);
	
	public abstract void eliminarPDesdeProveedor(String proveedor, int numRef);
	
	public abstract void borrarProducto(int numRef, String titulo);

	public abstract Integer obtenerStockProducto(int numRef);
	
	public abstract DTCliente mostrarPerfilCliente(String nick);
	 
	public abstract DTProveedor mostrarPerfilProveedor(String nick);
	 
	public abstract List<DTOrdenDeCompra> getOrdenesCliente(String nick);
	 
	public abstract Usuario getUserByEmail(String email) throws UsuarioException;
	 
	public abstract void agregarImagenesDesdeProveedor(String proveedor, int numRef, String imagen);
	 
	public abstract Producto getProdByCateogria(String cat, int numRef) throws ProductoException;
	   
	public abstract Producto getProducto(int numRef);
	   
	public abstract List<Producto> buscarProductos(String query);
	   
	public abstract List<Producto> getAllProductos();
	  
	public abstract void realizarCompra(OrdenDeCompra orden, String nickCliente);
	  
	public abstract List<Usuario> listaUsuarios();
	  
	public abstract DTOrdenDeCompra getOrden(int numero);
	  
	public abstract void cambiarEstadoOrden(String estado, String com, int numero, String cliente);
	  
	public abstract void notificarComentario(Producto producto, Comentario nuevoComentario, Comentario comentarioRespondido);
	  
	public abstract List<Cliente> obtenerClientesQueHanCompradoDelProveedor(Proveedor proveedor);
	  
	public abstract void notificarClientesNuevoProducto(Producto nuevoProducto, Proveedor proveedor);

	public abstract void agregarReclamo(String texto, LocalDateTime fecha, Producto p, Proveedor prov, Cliente autor) throws ReclamoException;

	public abstract Icon resizeIcon(ImageIcon imageIcon, int i, int j);

	public abstract void cambiarEstadoOrdenconDT(DTEstado estadoComprada11, int numeroOrden, String nick);
	
	public abstract List<Producto> obtenerProductosDestacados();

	public abstract void agregarImagenProd(String img, int numRef);
	
	public abstract Integer iniciarOrdenVacia(String nickProveedor);
	
	public abstract void realizarCompraPRUEBA(int orden, String nickCliente);
	
	public abstract void agregarItemsAOrden(int numeroOrden, int numProducto, int cantidad);
	
	public abstract void agregarComentario(int numRef, int comentarioId, String mensaje, String nickCliente) throws ProductoException;
	
	public abstract Cliente getClientePorCorreo(String email);
	  
	public abstract Cliente getClientePorToken(String token);

	public abstract Comentario getComentario(int id);

	public abstract List<String> listarSoloNombresCatProducto();

}