
package webservices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
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
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "verificarClienteYCrearCarrito", targetNamespace = "http://services/", className = "webservices.VerificarClienteYCrearCarrito")
    @ResponseWrapper(localName = "verificarClienteYCrearCarritoResponse", targetNamespace = "http://services/", className = "webservices.VerificarClienteYCrearCarritoResponse")
    @Action(input = "http://services/Publicador/verificarClienteYCrearCarritoRequest", output = "http://services/Publicador/verificarClienteYCrearCarritoResponse")
    public String verificarClienteYCrearCarrito(
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
    @RequestWrapper(localName = "comprobarSiProductoExisteCarrito", targetNamespace = "http://services/", className = "webservices.ComprobarSiProductoExisteCarrito")
    @ResponseWrapper(localName = "comprobarSiProductoExisteCarritoResponse", targetNamespace = "http://services/", className = "webservices.ComprobarSiProductoExisteCarritoResponse")
    @Action(input = "http://services/Publicador/comprobarSiProductoExisteCarritoRequest", output = "http://services/Publicador/comprobarSiProductoExisteCarritoResponse")
    public boolean comprobarSiProductoExisteCarrito(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerPrimeraImagenProducto", targetNamespace = "http://services/", className = "webservices.ObtenerPrimeraImagenProducto")
    @ResponseWrapper(localName = "obtenerPrimeraImagenProductoResponse", targetNamespace = "http://services/", className = "webservices.ObtenerPrimeraImagenProductoResponse")
    @Action(input = "http://services/Publicador/obtenerPrimeraImagenProductoRequest", output = "http://services/Publicador/obtenerPrimeraImagenProductoResponse")
    public String obtenerPrimeraImagenProducto(
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
    @RequestWrapper(localName = "obtenerPrimeraIMGProd", targetNamespace = "http://services/", className = "webservices.ObtenerPrimeraIMGProd")
    @ResponseWrapper(localName = "obtenerPrimeraIMGProdResponse", targetNamespace = "http://services/", className = "webservices.ObtenerPrimeraIMGProdResponse")
    @Action(input = "http://services/Publicador/obtenerPrimeraIMGProdRequest", output = "http://services/Publicador/obtenerPrimeraIMGProdResponse")
    public String obtenerPrimeraIMGProd(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns float
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPrecioTotalOrden", targetNamespace = "http://services/", className = "webservices.GetPrecioTotalOrden")
    @ResponseWrapper(localName = "getPrecioTotalOrdenResponse", targetNamespace = "http://services/", className = "webservices.GetPrecioTotalOrdenResponse")
    @Action(input = "http://services/Publicador/getPrecioTotalOrdenRequest", output = "http://services/Publicador/getPrecioTotalOrdenResponse")
    public float getPrecioTotalOrden(
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
    @RequestWrapper(localName = "getApellidoDTCliente", targetNamespace = "http://services/", className = "webservices.GetApellidoDTCliente")
    @ResponseWrapper(localName = "getApellidoDTClienteResponse", targetNamespace = "http://services/", className = "webservices.GetApellidoDTClienteResponse")
    @Action(input = "http://services/Publicador/getApellidoDTClienteRequest", output = "http://services/Publicador/getApellidoDTClienteResponse")
    public String getApellidoDTCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @throws CategoriaException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "agregarProductoCategoria", targetNamespace = "http://services/", className = "webservices.AgregarProductoCategoria")
    @ResponseWrapper(localName = "agregarProductoCategoriaResponse", targetNamespace = "http://services/", className = "webservices.AgregarProductoCategoriaResponse")
    @Action(input = "http://services/Publicador/agregarProductoCategoriaRequest", output = "http://services/Publicador/agregarProductoCategoriaResponse", fault = {
        @FaultAction(className = CategoriaException_Exception.class, value = "http://services/Publicador/agregarProductoCategoria/Fault/CategoriaException")
    })
    public void agregarProductoCategoria(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1)
        throws CategoriaException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Carrito
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCarritoDeCliente", targetNamespace = "http://services/", className = "webservices.ObtenerCarritoDeCliente")
    @ResponseWrapper(localName = "obtenerCarritoDeClienteResponse", targetNamespace = "http://services/", className = "webservices.ObtenerCarritoDeClienteResponse")
    @Action(input = "http://services/Publicador/obtenerCarritoDeClienteRequest", output = "http://services/Publicador/obtenerCarritoDeClienteResponse")
    public Carrito obtenerCarritoDeCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getImagenesDTCliente", targetNamespace = "http://services/", className = "webservices.GetImagenesDTCliente")
    @ResponseWrapper(localName = "getImagenesDTClienteResponse", targetNamespace = "http://services/", className = "webservices.GetImagenesDTClienteResponse")
    @Action(input = "http://services/Publicador/getImagenesDTClienteRequest", output = "http://services/Publicador/getImagenesDTClienteResponse")
    public String getImagenesDTCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<webservices.OrdenDeCompra>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listarComprasPorNick", targetNamespace = "http://services/", className = "webservices.ListarComprasPorNick")
    @ResponseWrapper(localName = "listarComprasPorNickResponse", targetNamespace = "http://services/", className = "webservices.ListarComprasPorNickResponse")
    @Action(input = "http://services/Publicador/listarComprasPorNickRequest", output = "http://services/Publicador/listarComprasPorNickResponse")
    public List<OrdenDeCompra> listarComprasPorNick(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getFechaNacDTClienteString", targetNamespace = "http://services/", className = "webservices.GetFechaNacDTClienteString")
    @ResponseWrapper(localName = "getFechaNacDTClienteStringResponse", targetNamespace = "http://services/", className = "webservices.GetFechaNacDTClienteStringResponse")
    @Action(input = "http://services/Publicador/getFechaNacDTClienteStringRequest", output = "http://services/Publicador/getFechaNacDTClienteStringResponse")
    public String getFechaNacDTClienteString(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns webservices.DtOrdenDeCompra
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "mostrarCompraCliente", targetNamespace = "http://services/", className = "webservices.MostrarCompraCliente")
    @ResponseWrapper(localName = "mostrarCompraClienteResponse", targetNamespace = "http://services/", className = "webservices.MostrarCompraClienteResponse")
    @Action(input = "http://services/Publicador/mostrarCompraClienteRequest", output = "http://services/Publicador/mostrarCompraClienteResponse")
    public DtOrdenDeCompra mostrarCompraCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        Cliente arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerImagenesProducto", targetNamespace = "http://services/", className = "webservices.ObtenerImagenesProducto")
    @ResponseWrapper(localName = "obtenerImagenesProductoResponse", targetNamespace = "http://services/", className = "webservices.ObtenerImagenesProductoResponse")
    @Action(input = "http://services/Publicador/obtenerImagenesProductoRequest", output = "http://services/Publicador/obtenerImagenesProductoResponse")
    public List<String> obtenerImagenesProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Carrito
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCarritoCliente", targetNamespace = "http://services/", className = "webservices.ObtenerCarritoCliente")
    @ResponseWrapper(localName = "obtenerCarritoClienteResponse", targetNamespace = "http://services/", className = "webservices.ObtenerCarritoClienteResponse")
    @Action(input = "http://services/Publicador/obtenerCarritoClienteRequest", output = "http://services/Publicador/obtenerCarritoClienteResponse")
    public Carrito obtenerCarritoCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saludar", targetNamespace = "http://services/", className = "webservices.Saludar")
    @ResponseWrapper(localName = "saludarResponse", targetNamespace = "http://services/", className = "webservices.SaludarResponse")
    @Action(input = "http://services/Publicador/saludarRequest", output = "http://services/Publicador/saludarResponse")
    public String saludar();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNombreUsuario", targetNamespace = "http://services/", className = "webservices.GetNombreUsuario")
    @ResponseWrapper(localName = "getNombreUsuarioResponse", targetNamespace = "http://services/", className = "webservices.GetNombreUsuarioResponse")
    @Action(input = "http://services/Publicador/getNombreUsuarioRequest", output = "http://services/Publicador/getNombreUsuarioResponse")
    public String getNombreUsuario(
        @WebParam(name = "arg0", targetNamespace = "")
        Usuario arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCantProdItem", targetNamespace = "http://services/", className = "webservices.GetCantProdItem")
    @ResponseWrapper(localName = "getCantProdItemResponse", targetNamespace = "http://services/", className = "webservices.GetCantProdItemResponse")
    @Action(input = "http://services/Publicador/getCantProdItemRequest", output = "http://services/Publicador/getCantProdItemResponse")
    public int getCantProdItem(
        @WebParam(name = "arg0", targetNamespace = "")
        DtItem arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.DtItem
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "crearDTItem", targetNamespace = "http://services/", className = "webservices.CrearDTItem")
    @ResponseWrapper(localName = "crearDTItemResponse", targetNamespace = "http://services/", className = "webservices.CrearDTItemResponse")
    @Action(input = "http://services/Publicador/crearDTItemRequest", output = "http://services/Publicador/crearDTItemResponse")
    public DtItem crearDTItem(
        @WebParam(name = "arg0", targetNamespace = "")
        Item arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNickCliente", targetNamespace = "http://services/", className = "webservices.GetNickCliente")
    @ResponseWrapper(localName = "getNickClienteResponse", targetNamespace = "http://services/", className = "webservices.GetNickClienteResponse")
    @Action(input = "http://services/Publicador/getNickClienteRequest", output = "http://services/Publicador/getNickClienteResponse")
    public String getNickCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        Cliente arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.DtOrdenDeCompra
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "crearDTOrden", targetNamespace = "http://services/", className = "webservices.CrearDTOrden")
    @ResponseWrapper(localName = "crearDTOrdenResponse", targetNamespace = "http://services/", className = "webservices.CrearDTOrdenResponse")
    @Action(input = "http://services/Publicador/crearDTOrdenRequest", output = "http://services/Publicador/crearDTOrdenResponse")
    public DtOrdenDeCompra crearDTOrden(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg5
     * @param arg4
     * @param arg1
     * @param arg0
     * @param arg6
     */
    @WebMethod
    @RequestWrapper(localName = "agregarProducto", targetNamespace = "http://services/", className = "webservices.AgregarProducto")
    @ResponseWrapper(localName = "agregarProductoResponse", targetNamespace = "http://services/", className = "webservices.AgregarProductoResponse")
    @Action(input = "http://services/Publicador/agregarProductoRequest", output = "http://services/Publicador/agregarProductoResponse")
    public void agregarProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        float arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        int arg6);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns webservices.DtEstado
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "crearEstado", targetNamespace = "http://services/", className = "webservices.CrearEstado")
    @ResponseWrapper(localName = "crearEstadoResponse", targetNamespace = "http://services/", className = "webservices.CrearEstadoResponse")
    @Action(input = "http://services/Publicador/crearEstadoRequest", output = "http://services/Publicador/crearEstadoResponse")
    public DtEstado crearEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns float
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPrecioProd", targetNamespace = "http://services/", className = "webservices.GetPrecioProd")
    @ResponseWrapper(localName = "getPrecioProdResponse", targetNamespace = "http://services/", className = "webservices.GetPrecioProdResponse")
    @Action(input = "http://services/Publicador/getPrecioProdRequest", output = "http://services/Publicador/getPrecioProdResponse")
    public float getPrecioProd(
        @WebParam(name = "arg0", targetNamespace = "")
        DtProducto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNickDTCliente", targetNamespace = "http://services/", className = "webservices.GetNickDTCliente")
    @ResponseWrapper(localName = "getNickDTClienteResponse", targetNamespace = "http://services/", className = "webservices.GetNickDTClienteResponse")
    @Action(input = "http://services/Publicador/getNickDTClienteRequest", output = "http://services/Publicador/getNickDTClienteResponse")
    public String getNickDTCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<webservices.Categoria>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategoriasLista", targetNamespace = "http://services/", className = "webservices.GetCategoriasLista")
    @ResponseWrapper(localName = "getCategoriasListaResponse", targetNamespace = "http://services/", className = "webservices.GetCategoriasListaResponse")
    @Action(input = "http://services/Publicador/getCategoriasListaRequest", output = "http://services/Publicador/getCategoriasListaResponse")
    public List<Categoria> getCategoriasLista();

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNumRefOrden", targetNamespace = "http://services/", className = "webservices.GetNumRefOrden")
    @ResponseWrapper(localName = "getNumRefOrdenResponse", targetNamespace = "http://services/", className = "webservices.GetNumRefOrdenResponse")
    @Action(input = "http://services/Publicador/getNumRefOrdenRequest", output = "http://services/Publicador/getNumRefOrdenResponse")
    public int getNumRefOrden(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<webservices.Item>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItemsOrden", targetNamespace = "http://services/", className = "webservices.GetItemsOrden")
    @ResponseWrapper(localName = "getItemsOrdenResponse", targetNamespace = "http://services/", className = "webservices.GetItemsOrdenResponse")
    @Action(input = "http://services/Publicador/getItemsOrdenRequest", output = "http://services/Publicador/getItemsOrdenResponse")
    public List<Item> getItemsOrden(
        @WebParam(name = "arg0", targetNamespace = "")
        DtOrdenDeCompra arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getFechaOrden", targetNamespace = "http://services/", className = "webservices.GetFechaOrden")
    @ResponseWrapper(localName = "getFechaOrdenResponse", targetNamespace = "http://services/", className = "webservices.GetFechaOrdenResponse")
    @Action(input = "http://services/Publicador/getFechaOrdenRequest", output = "http://services/Publicador/getFechaOrdenResponse")
    public String getFechaOrden(
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
    @RequestWrapper(localName = "getNombreDTCliente", targetNamespace = "http://services/", className = "webservices.GetNombreDTCliente")
    @ResponseWrapper(localName = "getNombreDTClienteResponse", targetNamespace = "http://services/", className = "webservices.GetNombreDTClienteResponse")
    @Action(input = "http://services/Publicador/getNombreDTClienteRequest", output = "http://services/Publicador/getNombreDTClienteResponse")
    public String getNombreDTCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Producto
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getProductoItem", targetNamespace = "http://services/", className = "webservices.GetProductoItem")
    @ResponseWrapper(localName = "getProductoItemResponse", targetNamespace = "http://services/", className = "webservices.GetProductoItemResponse")
    @Action(input = "http://services/Publicador/getProductoItemRequest", output = "http://services/Publicador/getProductoItemResponse")
    public Producto getProductoItem(
        @WebParam(name = "arg0", targetNamespace = "")
        DtItem arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<webservices.DtEstado>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getHistorialEstado", targetNamespace = "http://services/", className = "webservices.GetHistorialEstado")
    @ResponseWrapper(localName = "getHistorialEstadoResponse", targetNamespace = "http://services/", className = "webservices.GetHistorialEstadoResponse")
    @Action(input = "http://services/Publicador/getHistorialEstadoRequest", output = "http://services/Publicador/getHistorialEstadoResponse")
    public List<DtEstado> getHistorialEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        DtOrdenDeCompra arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns float
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSubTotaItem", targetNamespace = "http://services/", className = "webservices.GetSubTotaItem")
    @ResponseWrapper(localName = "getSubTotaItemResponse", targetNamespace = "http://services/", className = "webservices.GetSubTotaItemResponse")
    @Action(input = "http://services/Publicador/getSubTotaItemRequest", output = "http://services/Publicador/getSubTotaItemResponse")
    public float getSubTotaItem(
        @WebParam(name = "arg0", targetNamespace = "")
        DtItem arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "setCarritoCliente", targetNamespace = "http://services/", className = "webservices.SetCarritoCliente")
    @ResponseWrapper(localName = "setCarritoClienteResponse", targetNamespace = "http://services/", className = "webservices.SetCarritoClienteResponse")
    @Action(input = "http://services/Publicador/setCarritoClienteRequest", output = "http://services/Publicador/setCarritoClienteResponse")
    public void setCarritoCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Carrito arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "setEstadoOrden", targetNamespace = "http://services/", className = "webservices.SetEstadoOrden")
    @ResponseWrapper(localName = "setEstadoOrdenResponse", targetNamespace = "http://services/", className = "webservices.SetEstadoOrdenResponse")
    @Action(input = "http://services/Publicador/setEstadoOrdenRequest", output = "http://services/Publicador/setEstadoOrdenResponse")
    public void setEstadoOrden(
        @WebParam(name = "arg0", targetNamespace = "")
        OrdenDeCompra arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        DtEstado arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNombreProd", targetNamespace = "http://services/", className = "webservices.GetNombreProd")
    @ResponseWrapper(localName = "getNombreProdResponse", targetNamespace = "http://services/", className = "webservices.GetNombreProdResponse")
    @Action(input = "http://services/Publicador/getNombreProdRequest", output = "http://services/Publicador/getNombreProdResponse")
    public String getNombreProd(
        @WebParam(name = "arg0", targetNamespace = "")
        DtProducto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEstadoOrden", targetNamespace = "http://services/", className = "webservices.GetEstadoOrden")
    @ResponseWrapper(localName = "getEstadoOrdenResponse", targetNamespace = "http://services/", className = "webservices.GetEstadoOrdenResponse")
    @Action(input = "http://services/Publicador/getEstadoOrdenRequest", output = "http://services/Publicador/getEstadoOrdenResponse")
    public String getEstadoOrden(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<webservices.OrdenDeCompra>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOrdenesCliente", targetNamespace = "http://services/", className = "webservices.GetOrdenesCliente")
    @ResponseWrapper(localName = "getOrdenesClienteResponse", targetNamespace = "http://services/", className = "webservices.GetOrdenesClienteResponse")
    @Action(input = "http://services/Publicador/getOrdenesClienteRequest", output = "http://services/Publicador/getOrdenesClienteResponse")
    public List<OrdenDeCompra> getOrdenesCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getFechaEstado", targetNamespace = "http://services/", className = "webservices.GetFechaEstado")
    @ResponseWrapper(localName = "getFechaEstadoResponse", targetNamespace = "http://services/", className = "webservices.GetFechaEstadoResponse")
    @Action(input = "http://services/Publicador/getFechaEstadoRequest", output = "http://services/Publicador/getFechaEstadoResponse")
    public String getFechaEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        DtEstado arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getComEstado", targetNamespace = "http://services/", className = "webservices.GetComEstado")
    @ResponseWrapper(localName = "getComEstadoResponse", targetNamespace = "http://services/", className = "webservices.GetComEstadoResponse")
    @Action(input = "http://services/Publicador/getComEstadoRequest", output = "http://services/Publicador/getComEstadoResponse")
    public String getComEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        DtEstado arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.DtProducto
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "crearDTProd", targetNamespace = "http://services/", className = "webservices.CrearDTProd")
    @ResponseWrapper(localName = "crearDTProdResponse", targetNamespace = "http://services/", className = "webservices.CrearDTProdResponse")
    @Action(input = "http://services/Publicador/crearDTProdRequest", output = "http://services/Publicador/crearDTProdResponse")
    public DtProducto crearDTProd(
        @WebParam(name = "arg0", targetNamespace = "")
        Producto arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.DtCliente
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "crearDTCliente", targetNamespace = "http://services/", className = "webservices.CrearDTCliente")
    @ResponseWrapper(localName = "crearDTClienteResponse", targetNamespace = "http://services/", className = "webservices.CrearDTClienteResponse")
    @Action(input = "http://services/Publicador/crearDTClienteRequest", output = "http://services/Publicador/crearDTClienteResponse")
    public DtCliente crearDTCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        Cliente arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Cliente
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerCliente", targetNamespace = "http://services/", className = "webservices.ObtenerCliente")
    @ResponseWrapper(localName = "obtenerClienteResponse", targetNamespace = "http://services/", className = "webservices.ObtenerClienteResponse")
    @Action(input = "http://services/Publicador/obtenerClienteRequest", output = "http://services/Publicador/obtenerClienteResponse")
    public Cliente obtenerCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Producto
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProducto", targetNamespace = "http://services/", className = "webservices.ObtenerProducto")
    @ResponseWrapper(localName = "obtenerProductoResponse", targetNamespace = "http://services/", className = "webservices.ObtenerProductoResponse")
    @Action(input = "http://services/Publicador/obtenerProductoRequest", output = "http://services/Publicador/obtenerProductoResponse")
    public Producto obtenerProducto(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "comprobarCliente", targetNamespace = "http://services/", className = "webservices.ComprobarCliente")
    @ResponseWrapper(localName = "comprobarClienteResponse", targetNamespace = "http://services/", className = "webservices.ComprobarClienteResponse")
    @Action(input = "http://services/Publicador/comprobarClienteRequest", output = "http://services/Publicador/comprobarClienteResponse")
    public Boolean comprobarCliente(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Usuario
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerUsuario", targetNamespace = "http://services/", className = "webservices.ObtenerUsuario")
    @ResponseWrapper(localName = "obtenerUsuarioResponse", targetNamespace = "http://services/", className = "webservices.ObtenerUsuarioResponse")
    @Action(input = "http://services/Publicador/obtenerUsuarioRequest", output = "http://services/Publicador/obtenerUsuarioResponse")
    public Usuario obtenerUsuario(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns webservices.Proveedor
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProveedor", targetNamespace = "http://services/", className = "webservices.ObtenerProveedor")
    @ResponseWrapper(localName = "obtenerProveedorResponse", targetNamespace = "http://services/", className = "webservices.ObtenerProveedorResponse")
    @Action(input = "http://services/Publicador/obtenerProveedorRequest", output = "http://services/Publicador/obtenerProveedorResponse")
    public Proveedor obtenerProveedor(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<webservices.Producto>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerProductos", targetNamespace = "http://services/", className = "webservices.ObtenerProductos")
    @ResponseWrapper(localName = "obtenerProductosResponse", targetNamespace = "http://services/", className = "webservices.ObtenerProductosResponse")
    @Action(input = "http://services/Publicador/obtenerProductosRequest", output = "http://services/Publicador/obtenerProductosResponse")
    public List<Producto> obtenerProductos();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTipo", targetNamespace = "http://services/", className = "webservices.GetTipo")
    @ResponseWrapper(localName = "getTipoResponse", targetNamespace = "http://services/", className = "webservices.GetTipoResponse")
    @Action(input = "http://services/Publicador/getTipoRequest", output = "http://services/Publicador/getTipoResponse")
    public String getTipo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns webservices.OrdenDeCompra
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCompra", targetNamespace = "http://services/", className = "webservices.GetCompra")
    @ResponseWrapper(localName = "getCompraResponse", targetNamespace = "http://services/", className = "webservices.GetCompraResponse")
    @Action(input = "http://services/Publicador/getCompraRequest", output = "http://services/Publicador/getCompraResponse")
    public OrdenDeCompra getCompra(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Cliente arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEstado", targetNamespace = "http://services/", className = "webservices.GetEstado")
    @ResponseWrapper(localName = "getEstadoResponse", targetNamespace = "http://services/", className = "webservices.GetEstadoResponse")
    @Action(input = "http://services/Publicador/getEstadoRequest", output = "http://services/Publicador/getEstadoResponse")
    public String getEstado(
        @WebParam(name = "arg0", targetNamespace = "")
        DtEstado arg0);

}