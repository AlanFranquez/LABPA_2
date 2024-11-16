
package services;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Publicador", targetNamespace = "http://services/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Publicador {


    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saludar", targetNamespace = "http://services/", className = "services.Saludar")
    @ResponseWrapper(localName = "saludarResponse", targetNamespace = "http://services/", className = "services.SaludarResponse")
    @Action(input = "http://services/Publicador/saludarRequest", output = "http://services/Publicador/saludarResponse")
    public String saludar();

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Producto
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProducto", targetNamespace = "http://services/", className = "services.ObtenerProducto")
    @ResponseWrapper(localName = "obtenerProductoResponse", targetNamespace = "http://services/", className = "services.ObtenerProductoResponse")
    @Action(input = "http://services/Publicador/obtenerProductoRequest", output = "http://services/Publicador/obtenerProductoResponse")
    public Producto obtenerProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerPrimeraIMGProd", targetNamespace = "http://services/", className = "services.ObtenerPrimeraIMGProd")
    @ResponseWrapper(localName = "obtenerPrimeraIMGProdResponse", targetNamespace = "http://services/", className = "services.ObtenerPrimeraIMGProdResponse")
    @Action(input = "http://services/Publicador/obtenerPrimeraIMGProdRequest", output = "http://services/Publicador/obtenerPrimeraIMGProdResponse")
    public String obtenerPrimeraIMGProd(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "verificarClienteYCrearCarrito", targetNamespace = "http://services/", className = "services.VerificarClienteYCrearCarrito")
    @ResponseWrapper(localName = "verificarClienteYCrearCarritoResponse", targetNamespace = "http://services/", className = "services.VerificarClienteYCrearCarritoResponse")
    @Action(input = "http://services/Publicador/verificarClienteYCrearCarritoRequest", output = "http://services/Publicador/verificarClienteYCrearCarritoResponse")
    public String verificarClienteYCrearCarrito(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Carrito
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCarritoDeCliente", targetNamespace = "http://services/", className = "services.ObtenerCarritoDeCliente")
    @ResponseWrapper(localName = "obtenerCarritoDeClienteResponse", targetNamespace = "http://services/", className = "services.ObtenerCarritoDeClienteResponse")
    @Action(input = "http://services/Publicador/obtenerCarritoDeClienteRequest", output = "http://services/Publicador/obtenerCarritoDeClienteResponse")
    public Carrito obtenerCarritoDeCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "comprobarCliente", targetNamespace = "http://services/", className = "services.ComprobarCliente")
    @ResponseWrapper(localName = "comprobarClienteResponse", targetNamespace = "http://services/", className = "services.ComprobarClienteResponse")
    @Action(input = "http://services/Publicador/comprobarClienteRequest", output = "http://services/Publicador/comprobarClienteResponse")
    public Boolean comprobarCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<services.Producto>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProductos", targetNamespace = "http://services/", className = "services.ObtenerProductos")
    @ResponseWrapper(localName = "obtenerProductosResponse", targetNamespace = "http://services/", className = "services.ObtenerProductosResponse")
    @Action(input = "http://services/Publicador/obtenerProductosRequest", output = "http://services/Publicador/obtenerProductosResponse")
    public List<Producto> obtenerProductos();

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Usuario
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerUsuario", targetNamespace = "http://services/", className = "services.ObtenerUsuario")
    @ResponseWrapper(localName = "obtenerUsuarioResponse", targetNamespace = "http://services/", className = "services.ObtenerUsuarioResponse")
    @Action(input = "http://services/Publicador/obtenerUsuarioRequest", output = "http://services/Publicador/obtenerUsuarioResponse")
    public Usuario obtenerUsuario(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Cliente
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCliente", targetNamespace = "http://services/", className = "services.ObtenerCliente")
    @ResponseWrapper(localName = "obtenerClienteResponse", targetNamespace = "http://services/", className = "services.ObtenerClienteResponse")
    @Action(input = "http://services/Publicador/obtenerClienteRequest", output = "http://services/Publicador/obtenerClienteResponse")
    public Cliente obtenerCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Proveedor
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProveedor", targetNamespace = "http://services/", className = "services.ObtenerProveedor")
    @ResponseWrapper(localName = "obtenerProveedorResponse", targetNamespace = "http://services/", className = "services.ObtenerProveedorResponse")
    @Action(input = "http://services/Publicador/obtenerProveedorRequest", output = "http://services/Publicador/obtenerProveedorResponse")
    public Proveedor obtenerProveedor(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns services.Carrito
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCarritoCliente", targetNamespace = "http://services/", className = "services.ObtenerCarritoCliente")
    @ResponseWrapper(localName = "obtenerCarritoClienteResponse", targetNamespace = "http://services/", className = "services.ObtenerCarritoClienteResponse")
    @Action(input = "http://services/Publicador/obtenerCarritoClienteRequest", output = "http://services/Publicador/obtenerCarritoClienteResponse")
    public Carrito obtenerCarritoCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "comprobarSiProductoExisteCarrito", targetNamespace = "http://services/", className = "services.ComprobarSiProductoExisteCarrito")
    @ResponseWrapper(localName = "comprobarSiProductoExisteCarritoResponse", targetNamespace = "http://services/", className = "services.ComprobarSiProductoExisteCarritoResponse")
    @Action(input = "http://services/Publicador/comprobarSiProductoExisteCarritoRequest", output = "http://services/Publicador/comprobarSiProductoExisteCarritoResponse")
    public boolean comprobarSiProductoExisteCarrito(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "setCarritoCliente", targetNamespace = "http://services/", className = "services.SetCarritoCliente")
    @ResponseWrapper(localName = "setCarritoClienteResponse", targetNamespace = "http://services/", className = "services.SetCarritoClienteResponse")
    @Action(input = "http://services/Publicador/setCarritoClienteRequest", output = "http://services/Publicador/setCarritoClienteResponse")
    public void setCarritoCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Carrito arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerPrimeraImagenProducto", targetNamespace = "http://services/", className = "services.ObtenerPrimeraImagenProducto")
    @ResponseWrapper(localName = "obtenerPrimeraImagenProductoResponse", targetNamespace = "http://services/", className = "services.ObtenerPrimeraImagenProductoResponse")
    @Action(input = "http://services/Publicador/obtenerPrimeraImagenProductoRequest", output = "http://services/Publicador/obtenerPrimeraImagenProductoResponse")
    public String obtenerPrimeraImagenProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerImagenesProducto", targetNamespace = "http://services/", className = "services.ObtenerImagenesProducto")
    @ResponseWrapper(localName = "obtenerImagenesProductoResponse", targetNamespace = "http://services/", className = "services.ObtenerImagenesProductoResponse")
    @Action(input = "http://services/Publicador/obtenerImagenesProductoRequest", output = "http://services/Publicador/obtenerImagenesProductoResponse")
    public List<String> obtenerImagenesProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

}