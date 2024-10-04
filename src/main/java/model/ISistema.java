package model;

import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import exceptions.CategoriaException;
import exceptions.OrdenDeCompraException;
import exceptions.ProductoException;
import exceptions.UsuarioRepetidoException;

public interface ISistema {
    public abstract boolean validarCorreo(String correo);
    
    public void agregarProveedor(String nick, String correo, String nombre, String apellido, DTFecha fechaNacimiento, String compania, String link, String contra, String confContra) throws UsuarioRepetidoException;
    
    public void agregarCliente(String nombre, String nick, String apellido, String correo, DTFecha fecha, String contra, String confContra) throws UsuarioRepetidoException;
    
    public abstract void agregarImagenUsuario(String nick, ImageIcon imageIcon);
    
    public abstract List<DTCliente> listarClientes();
    
    public abstract Usuario getUsuario(String nickname);
    
    public abstract boolean existeCategoria(String nombre);
    
    public abstract List <String> listarSoloNombresPadresCat();
    
    public abstract void agregarCategoria(String nombre) throws CategoriaException;
    
    public abstract void agregarCategoriaConProductos(String nombre) throws CategoriaException;
    
    public abstract void asignarlePadreCategoria(String nombrePadre, String nombre) throws CategoriaException;
    
    public abstract void asignarlePadreACategoriaProds(String nombrePadre, String nombre) throws CategoriaException;
    
    public abstract List<DTOrdenDeCompra> listarOrdenes();
    
    public abstract boolean existenOrdenesParaListar();

	public abstract DefaultMutableTreeNode arbolCategorias();

	public abstract DefaultMutableTreeNode arbolProductos();

	public abstract List<DTProveedor> listarProveedores();

    public abstract void addOrdenes(OrdenDeCompra o, String nickUsuario);

	public abstract void agregarProductoCategoria(String catName, int numRef) throws CategoriaException;

	public abstract boolean esPadre(String catName);

	public abstract void agregarProducto(String titulo, int numRef, String descripcion, String especificaciones,
			int precio, String proveedor, int stock);

	public abstract void agregarProducto(int numRef, int cant);

	public abstract void CrearOrden();
	
	public abstract List<DtProducto> listarALLProductos() throws ProductoException;
	
	public abstract List<DtProducto> listarProductosPorCategoria(String cat) throws ProductoException;
	
	public boolean comprobarCat(String cat) throws CategoriaException;

	public abstract void asignarOrdenCliente(String cliente);

	public abstract DtProducto getDtProducto(int numRef);

	public abstract Icon resizeIcon(ImageIcon imageIcon, int i, int j);

	public abstract void eliminarOrdenDeCompra(int numero) throws OrdenDeCompraException;

	public abstract boolean existeOrden(int i);
	
	public abstract void eliminarUltimaOrden();
	
	public abstract boolean verificarUnicidadProducto(String nombreCategoria, int numRef, String titulo);
	
	public abstract void eliminarPDesdeProveedor(String proveedor, int numRef);
	
	public abstract void agregarImagenesProducto(String cat, int num, File imagen);
	
	public abstract void borrarProducto(int numRef, String titulo);

	public abstract Integer obtenerStockProducto(int numRef);
	
	 // MOSTRAR PERFIL CLIENTE
	 public DTCliente mostrarPerfilCliente(String nick);
	 
	 // MOSTRAR PERFIL PROVEEDOR
	 public DTProveedor mostrarPerfilProveedor(String nick);
	 
	 // Traer Ordenes de compras de un cliente
	 public List<DTOrdenDeCompra> getOrdenesCliente(String nick);
}
