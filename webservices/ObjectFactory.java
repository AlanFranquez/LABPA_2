
package webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ObtenerUsuarioResponse_QNAME = new QName("http://services/", "obtenerUsuarioResponse");
    private final static QName _ComprobarCliente_QNAME = new QName("http://services/", "comprobarCliente");
    private final static QName _ComprobarSiProductoExisteCarritoResponse_QNAME = new QName("http://services/", "comprobarSiProductoExisteCarritoResponse");
    private final static QName _ComprobarSiProductoExisteCarrito_QNAME = new QName("http://services/", "comprobarSiProductoExisteCarrito");
    private final static QName _ObtenerProductosResponse_QNAME = new QName("http://services/", "obtenerProductosResponse");
    private final static QName _SetCarritoClienteResponse_QNAME = new QName("http://services/", "setCarritoClienteResponse");
    private final static QName _ObtenerPrimeraImagenProducto_QNAME = new QName("http://services/", "obtenerPrimeraImagenProducto");
    private final static QName _ComprobarClienteResponse_QNAME = new QName("http://services/", "comprobarClienteResponse");
    private final static QName _ObtenerCarritoClienteResponse_QNAME = new QName("http://services/", "obtenerCarritoClienteResponse");
    private final static QName _ObtenerProveedorResponse_QNAME = new QName("http://services/", "obtenerProveedorResponse");
    private final static QName _GetCategoriasLista_QNAME = new QName("http://services/", "getCategoriasLista");
    private final static QName _ObtenerProveedor_QNAME = new QName("http://services/", "obtenerProveedor");
    private final static QName _ObtenerProductos_QNAME = new QName("http://services/", "obtenerProductos");
    private final static QName _VerificarClienteYCrearCarrito_QNAME = new QName("http://services/", "verificarClienteYCrearCarrito");
    private final static QName _ObtenerCliente_QNAME = new QName("http://services/", "obtenerCliente");
    private final static QName _ObtenerCarritoDeCliente_QNAME = new QName("http://services/", "obtenerCarritoDeCliente");
    private final static QName _ObtenerPrimeraIMGProdResponse_QNAME = new QName("http://services/", "obtenerPrimeraIMGProdResponse");
    private final static QName _Saludar_QNAME = new QName("http://services/", "saludar");
    private final static QName _GetCategoriasListaResponse_QNAME = new QName("http://services/", "getCategoriasListaResponse");
    private final static QName _ObtenerImagenesProducto_QNAME = new QName("http://services/", "obtenerImagenesProducto");
    private final static QName _ObtenerPrimeraImagenProductoResponse_QNAME = new QName("http://services/", "obtenerPrimeraImagenProductoResponse");
    private final static QName _ObtenerUsuario_QNAME = new QName("http://services/", "obtenerUsuario");
    private final static QName _SaludarResponse_QNAME = new QName("http://services/", "saludarResponse");
    private final static QName _ObtenerCarritoCliente_QNAME = new QName("http://services/", "obtenerCarritoCliente");
    private final static QName _ObtenerImagenesProductoResponse_QNAME = new QName("http://services/", "obtenerImagenesProductoResponse");
    private final static QName _ObtenerCarritoDeClienteResponse_QNAME = new QName("http://services/", "obtenerCarritoDeClienteResponse");
    private final static QName _ObtenerProductoResponse_QNAME = new QName("http://services/", "obtenerProductoResponse");
    private final static QName _ObtenerProducto_QNAME = new QName("http://services/", "obtenerProducto");
    private final static QName _ObtenerClienteResponse_QNAME = new QName("http://services/", "obtenerClienteResponse");
    private final static QName _VerificarClienteYCrearCarritoResponse_QNAME = new QName("http://services/", "verificarClienteYCrearCarritoResponse");
    private final static QName _ObtenerPrimeraIMGProd_QNAME = new QName("http://services/", "obtenerPrimeraIMGProd");
    private final static QName _SetCarritoCliente_QNAME = new QName("http://services/", "setCarritoCliente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerCarritoDeCliente }
     * 
     */
    public ObtenerCarritoDeCliente createObtenerCarritoDeCliente() {
        return new ObtenerCarritoDeCliente();
    }

    /**
     * Create an instance of {@link ObtenerPrimeraIMGProdResponse }
     * 
     */
    public ObtenerPrimeraIMGProdResponse createObtenerPrimeraIMGProdResponse() {
        return new ObtenerPrimeraIMGProdResponse();
    }

    /**
     * Create an instance of {@link Saludar }
     * 
     */
    public Saludar createSaludar() {
        return new Saludar();
    }

    /**
     * Create an instance of {@link ObtenerCliente }
     * 
     */
    public ObtenerCliente createObtenerCliente() {
        return new ObtenerCliente();
    }

    /**
     * Create an instance of {@link GetCategoriasLista }
     * 
     */
    public GetCategoriasLista createGetCategoriasLista() {
        return new GetCategoriasLista();
    }

    /**
     * Create an instance of {@link ObtenerProveedor }
     * 
     */
    public ObtenerProveedor createObtenerProveedor() {
        return new ObtenerProveedor();
    }

    /**
     * Create an instance of {@link ObtenerProductos }
     * 
     */
    public ObtenerProductos createObtenerProductos() {
        return new ObtenerProductos();
    }

    /**
     * Create an instance of {@link VerificarClienteYCrearCarrito }
     * 
     */
    public VerificarClienteYCrearCarrito createVerificarClienteYCrearCarrito() {
        return new VerificarClienteYCrearCarrito();
    }

    /**
     * Create an instance of {@link ObtenerPrimeraImagenProducto }
     * 
     */
    public ObtenerPrimeraImagenProducto createObtenerPrimeraImagenProducto() {
        return new ObtenerPrimeraImagenProducto();
    }

    /**
     * Create an instance of {@link ObtenerCarritoClienteResponse }
     * 
     */
    public ObtenerCarritoClienteResponse createObtenerCarritoClienteResponse() {
        return new ObtenerCarritoClienteResponse();
    }

    /**
     * Create an instance of {@link ObtenerProveedorResponse }
     * 
     */
    public ObtenerProveedorResponse createObtenerProveedorResponse() {
        return new ObtenerProveedorResponse();
    }

    /**
     * Create an instance of {@link ComprobarClienteResponse }
     * 
     */
    public ComprobarClienteResponse createComprobarClienteResponse() {
        return new ComprobarClienteResponse();
    }

    /**
     * Create an instance of {@link ObtenerUsuarioResponse }
     * 
     */
    public ObtenerUsuarioResponse createObtenerUsuarioResponse() {
        return new ObtenerUsuarioResponse();
    }

    /**
     * Create an instance of {@link ComprobarCliente }
     * 
     */
    public ComprobarCliente createComprobarCliente() {
        return new ComprobarCliente();
    }

    /**
     * Create an instance of {@link ComprobarSiProductoExisteCarritoResponse }
     * 
     */
    public ComprobarSiProductoExisteCarritoResponse createComprobarSiProductoExisteCarritoResponse() {
        return new ComprobarSiProductoExisteCarritoResponse();
    }

    /**
     * Create an instance of {@link ObtenerProductosResponse }
     * 
     */
    public ObtenerProductosResponse createObtenerProductosResponse() {
        return new ObtenerProductosResponse();
    }

    /**
     * Create an instance of {@link SetCarritoClienteResponse }
     * 
     */
    public SetCarritoClienteResponse createSetCarritoClienteResponse() {
        return new SetCarritoClienteResponse();
    }

    /**
     * Create an instance of {@link ComprobarSiProductoExisteCarrito }
     * 
     */
    public ComprobarSiProductoExisteCarrito createComprobarSiProductoExisteCarrito() {
        return new ComprobarSiProductoExisteCarrito();
    }

    /**
     * Create an instance of {@link SetCarritoCliente }
     * 
     */
    public SetCarritoCliente createSetCarritoCliente() {
        return new SetCarritoCliente();
    }

    /**
     * Create an instance of {@link ObtenerProducto }
     * 
     */
    public ObtenerProducto createObtenerProducto() {
        return new ObtenerProducto();
    }

    /**
     * Create an instance of {@link ObtenerClienteResponse }
     * 
     */
    public ObtenerClienteResponse createObtenerClienteResponse() {
        return new ObtenerClienteResponse();
    }

    /**
     * Create an instance of {@link VerificarClienteYCrearCarritoResponse }
     * 
     */
    public VerificarClienteYCrearCarritoResponse createVerificarClienteYCrearCarritoResponse() {
        return new VerificarClienteYCrearCarritoResponse();
    }

    /**
     * Create an instance of {@link ObtenerPrimeraIMGProd }
     * 
     */
    public ObtenerPrimeraIMGProd createObtenerPrimeraIMGProd() {
        return new ObtenerPrimeraIMGProd();
    }

    /**
     * Create an instance of {@link ObtenerCarritoDeClienteResponse }
     * 
     */
    public ObtenerCarritoDeClienteResponse createObtenerCarritoDeClienteResponse() {
        return new ObtenerCarritoDeClienteResponse();
    }

    /**
     * Create an instance of {@link ObtenerProductoResponse }
     * 
     */
    public ObtenerProductoResponse createObtenerProductoResponse() {
        return new ObtenerProductoResponse();
    }

    /**
     * Create an instance of {@link ObtenerPrimeraImagenProductoResponse }
     * 
     */
    public ObtenerPrimeraImagenProductoResponse createObtenerPrimeraImagenProductoResponse() {
        return new ObtenerPrimeraImagenProductoResponse();
    }

    /**
     * Create an instance of {@link ObtenerUsuario }
     * 
     */
    public ObtenerUsuario createObtenerUsuario() {
        return new ObtenerUsuario();
    }

    /**
     * Create an instance of {@link SaludarResponse }
     * 
     */
    public SaludarResponse createSaludarResponse() {
        return new SaludarResponse();
    }

    /**
     * Create an instance of {@link GetCategoriasListaResponse }
     * 
     */
    public GetCategoriasListaResponse createGetCategoriasListaResponse() {
        return new GetCategoriasListaResponse();
    }

    /**
     * Create an instance of {@link ObtenerImagenesProducto }
     * 
     */
    public ObtenerImagenesProducto createObtenerImagenesProducto() {
        return new ObtenerImagenesProducto();
    }

    /**
     * Create an instance of {@link ObtenerCarritoCliente }
     * 
     */
    public ObtenerCarritoCliente createObtenerCarritoCliente() {
        return new ObtenerCarritoCliente();
    }

    /**
     * Create an instance of {@link ObtenerImagenesProductoResponse }
     * 
     */
    public ObtenerImagenesProductoResponse createObtenerImagenesProductoResponse() {
        return new ObtenerImagenesProductoResponse();
    }

    /**
     * Create an instance of {@link CatPadre }
     * 
     */
    public CatPadre createCatPadre() {
        return new CatPadre();
    }

    /**
     * Create an instance of {@link Producto }
     * 
     */
    public Producto createProducto() {
        return new Producto();
    }

    /**
     * Create an instance of {@link Carrito }
     * 
     */
    public Carrito createCarrito() {
        return new Carrito();
    }

    /**
     * Create an instance of {@link DtFecha }
     * 
     */
    public DtFecha createDtFecha() {
        return new DtFecha();
    }

    /**
     * Create an instance of {@link Categoria }
     * 
     */
    public Categoria createCategoria() {
        return new Categoria();
    }

    /**
     * Create an instance of {@link Cliente }
     * 
     */
    public Cliente createCliente() {
        return new Cliente();
    }

    /**
     * Create an instance of {@link Proveedor }
     * 
     */
    public Proveedor createProveedor() {
        return new Proveedor();
    }

    /**
     * Create an instance of {@link Usuario }
     * 
     */
    public Usuario createUsuario() {
        return new Usuario();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerUsuarioResponse")
    public JAXBElement<ObtenerUsuarioResponse> createObtenerUsuarioResponse(ObtenerUsuarioResponse value) {
        return new JAXBElement<ObtenerUsuarioResponse>(_ObtenerUsuarioResponse_QNAME, ObtenerUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "comprobarCliente")
    public JAXBElement<ComprobarCliente> createComprobarCliente(ComprobarCliente value) {
        return new JAXBElement<ComprobarCliente>(_ComprobarCliente_QNAME, ComprobarCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarSiProductoExisteCarritoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "comprobarSiProductoExisteCarritoResponse")
    public JAXBElement<ComprobarSiProductoExisteCarritoResponse> createComprobarSiProductoExisteCarritoResponse(ComprobarSiProductoExisteCarritoResponse value) {
        return new JAXBElement<ComprobarSiProductoExisteCarritoResponse>(_ComprobarSiProductoExisteCarritoResponse_QNAME, ComprobarSiProductoExisteCarritoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarSiProductoExisteCarrito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "comprobarSiProductoExisteCarrito")
    public JAXBElement<ComprobarSiProductoExisteCarrito> createComprobarSiProductoExisteCarrito(ComprobarSiProductoExisteCarrito value) {
        return new JAXBElement<ComprobarSiProductoExisteCarrito>(_ComprobarSiProductoExisteCarrito_QNAME, ComprobarSiProductoExisteCarrito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProductosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProductosResponse")
    public JAXBElement<ObtenerProductosResponse> createObtenerProductosResponse(ObtenerProductosResponse value) {
        return new JAXBElement<ObtenerProductosResponse>(_ObtenerProductosResponse_QNAME, ObtenerProductosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCarritoClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "setCarritoClienteResponse")
    public JAXBElement<SetCarritoClienteResponse> createSetCarritoClienteResponse(SetCarritoClienteResponse value) {
        return new JAXBElement<SetCarritoClienteResponse>(_SetCarritoClienteResponse_QNAME, SetCarritoClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPrimeraImagenProducto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerPrimeraImagenProducto")
    public JAXBElement<ObtenerPrimeraImagenProducto> createObtenerPrimeraImagenProducto(ObtenerPrimeraImagenProducto value) {
        return new JAXBElement<ObtenerPrimeraImagenProducto>(_ObtenerPrimeraImagenProducto_QNAME, ObtenerPrimeraImagenProducto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComprobarClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "comprobarClienteResponse")
    public JAXBElement<ComprobarClienteResponse> createComprobarClienteResponse(ComprobarClienteResponse value) {
        return new JAXBElement<ComprobarClienteResponse>(_ComprobarClienteResponse_QNAME, ComprobarClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCarritoClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerCarritoClienteResponse")
    public JAXBElement<ObtenerCarritoClienteResponse> createObtenerCarritoClienteResponse(ObtenerCarritoClienteResponse value) {
        return new JAXBElement<ObtenerCarritoClienteResponse>(_ObtenerCarritoClienteResponse_QNAME, ObtenerCarritoClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProveedorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProveedorResponse")
    public JAXBElement<ObtenerProveedorResponse> createObtenerProveedorResponse(ObtenerProveedorResponse value) {
        return new JAXBElement<ObtenerProveedorResponse>(_ObtenerProveedorResponse_QNAME, ObtenerProveedorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategoriasLista }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "getCategoriasLista")
    public JAXBElement<GetCategoriasLista> createGetCategoriasLista(GetCategoriasLista value) {
        return new JAXBElement<GetCategoriasLista>(_GetCategoriasLista_QNAME, GetCategoriasLista.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProveedor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProveedor")
    public JAXBElement<ObtenerProveedor> createObtenerProveedor(ObtenerProveedor value) {
        return new JAXBElement<ObtenerProveedor>(_ObtenerProveedor_QNAME, ObtenerProveedor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProductos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProductos")
    public JAXBElement<ObtenerProductos> createObtenerProductos(ObtenerProductos value) {
        return new JAXBElement<ObtenerProductos>(_ObtenerProductos_QNAME, ObtenerProductos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificarClienteYCrearCarrito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "verificarClienteYCrearCarrito")
    public JAXBElement<VerificarClienteYCrearCarrito> createVerificarClienteYCrearCarrito(VerificarClienteYCrearCarrito value) {
        return new JAXBElement<VerificarClienteYCrearCarrito>(_VerificarClienteYCrearCarrito_QNAME, VerificarClienteYCrearCarrito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerCliente")
    public JAXBElement<ObtenerCliente> createObtenerCliente(ObtenerCliente value) {
        return new JAXBElement<ObtenerCliente>(_ObtenerCliente_QNAME, ObtenerCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCarritoDeCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerCarritoDeCliente")
    public JAXBElement<ObtenerCarritoDeCliente> createObtenerCarritoDeCliente(ObtenerCarritoDeCliente value) {
        return new JAXBElement<ObtenerCarritoDeCliente>(_ObtenerCarritoDeCliente_QNAME, ObtenerCarritoDeCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPrimeraIMGProdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerPrimeraIMGProdResponse")
    public JAXBElement<ObtenerPrimeraIMGProdResponse> createObtenerPrimeraIMGProdResponse(ObtenerPrimeraIMGProdResponse value) {
        return new JAXBElement<ObtenerPrimeraIMGProdResponse>(_ObtenerPrimeraIMGProdResponse_QNAME, ObtenerPrimeraIMGProdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Saludar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "saludar")
    public JAXBElement<Saludar> createSaludar(Saludar value) {
        return new JAXBElement<Saludar>(_Saludar_QNAME, Saludar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategoriasListaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "getCategoriasListaResponse")
    public JAXBElement<GetCategoriasListaResponse> createGetCategoriasListaResponse(GetCategoriasListaResponse value) {
        return new JAXBElement<GetCategoriasListaResponse>(_GetCategoriasListaResponse_QNAME, GetCategoriasListaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerImagenesProducto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerImagenesProducto")
    public JAXBElement<ObtenerImagenesProducto> createObtenerImagenesProducto(ObtenerImagenesProducto value) {
        return new JAXBElement<ObtenerImagenesProducto>(_ObtenerImagenesProducto_QNAME, ObtenerImagenesProducto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPrimeraImagenProductoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerPrimeraImagenProductoResponse")
    public JAXBElement<ObtenerPrimeraImagenProductoResponse> createObtenerPrimeraImagenProductoResponse(ObtenerPrimeraImagenProductoResponse value) {
        return new JAXBElement<ObtenerPrimeraImagenProductoResponse>(_ObtenerPrimeraImagenProductoResponse_QNAME, ObtenerPrimeraImagenProductoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerUsuario")
    public JAXBElement<ObtenerUsuario> createObtenerUsuario(ObtenerUsuario value) {
        return new JAXBElement<ObtenerUsuario>(_ObtenerUsuario_QNAME, ObtenerUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaludarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "saludarResponse")
    public JAXBElement<SaludarResponse> createSaludarResponse(SaludarResponse value) {
        return new JAXBElement<SaludarResponse>(_SaludarResponse_QNAME, SaludarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCarritoCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerCarritoCliente")
    public JAXBElement<ObtenerCarritoCliente> createObtenerCarritoCliente(ObtenerCarritoCliente value) {
        return new JAXBElement<ObtenerCarritoCliente>(_ObtenerCarritoCliente_QNAME, ObtenerCarritoCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerImagenesProductoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerImagenesProductoResponse")
    public JAXBElement<ObtenerImagenesProductoResponse> createObtenerImagenesProductoResponse(ObtenerImagenesProductoResponse value) {
        return new JAXBElement<ObtenerImagenesProductoResponse>(_ObtenerImagenesProductoResponse_QNAME, ObtenerImagenesProductoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerCarritoDeClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerCarritoDeClienteResponse")
    public JAXBElement<ObtenerCarritoDeClienteResponse> createObtenerCarritoDeClienteResponse(ObtenerCarritoDeClienteResponse value) {
        return new JAXBElement<ObtenerCarritoDeClienteResponse>(_ObtenerCarritoDeClienteResponse_QNAME, ObtenerCarritoDeClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProductoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProductoResponse")
    public JAXBElement<ObtenerProductoResponse> createObtenerProductoResponse(ObtenerProductoResponse value) {
        return new JAXBElement<ObtenerProductoResponse>(_ObtenerProductoResponse_QNAME, ObtenerProductoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProducto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerProducto")
    public JAXBElement<ObtenerProducto> createObtenerProducto(ObtenerProducto value) {
        return new JAXBElement<ObtenerProducto>(_ObtenerProducto_QNAME, ObtenerProducto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerClienteResponse")
    public JAXBElement<ObtenerClienteResponse> createObtenerClienteResponse(ObtenerClienteResponse value) {
        return new JAXBElement<ObtenerClienteResponse>(_ObtenerClienteResponse_QNAME, ObtenerClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificarClienteYCrearCarritoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "verificarClienteYCrearCarritoResponse")
    public JAXBElement<VerificarClienteYCrearCarritoResponse> createVerificarClienteYCrearCarritoResponse(VerificarClienteYCrearCarritoResponse value) {
        return new JAXBElement<VerificarClienteYCrearCarritoResponse>(_VerificarClienteYCrearCarritoResponse_QNAME, VerificarClienteYCrearCarritoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerPrimeraIMGProd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "obtenerPrimeraIMGProd")
    public JAXBElement<ObtenerPrimeraIMGProd> createObtenerPrimeraIMGProd(ObtenerPrimeraIMGProd value) {
        return new JAXBElement<ObtenerPrimeraIMGProd>(_ObtenerPrimeraIMGProd_QNAME, ObtenerPrimeraIMGProd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCarritoCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "setCarritoCliente")
    public JAXBElement<SetCarritoCliente> createSetCarritoCliente(SetCarritoCliente value) {
        return new JAXBElement<SetCarritoCliente>(_SetCarritoCliente_QNAME, SetCarritoCliente.class, null, value);
    }

}
