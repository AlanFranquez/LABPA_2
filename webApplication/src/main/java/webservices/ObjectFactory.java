
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

    private final static QName _Saludar_QNAME = new QName("http://services.market.com/", "saludar");
    private final static QName _ObtenerProductoResponse_QNAME = new QName("http://services.market.com/", "obtenerProductoResponse");
    private final static QName _ListarProductosResponse_QNAME = new QName("http://services.market.com/", "listarProductosResponse");
    private final static QName _SaludarResponse_QNAME = new QName("http://services.market.com/", "saludarResponse");
    private final static QName _ListarProductos_QNAME = new QName("http://services.market.com/", "listarProductos");
    private final static QName _ObtenerProducto_QNAME = new QName("http://services.market.com/", "obtenerProducto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListarProductosResponse }
     * 
     */
    public ListarProductosResponse createListarProductosResponse() {
        return new ListarProductosResponse();
    }

    /**
     * Create an instance of {@link Saludar }
     * 
     */
    public Saludar createSaludar() {
        return new Saludar();
    }

    /**
     * Create an instance of {@link ObtenerProductoResponse }
     * 
     */
    public ObtenerProductoResponse createObtenerProductoResponse() {
        return new ObtenerProductoResponse();
    }

    /**
     * Create an instance of {@link SaludarResponse }
     * 
     */
    public SaludarResponse createSaludarResponse() {
        return new SaludarResponse();
    }

    /**
     * Create an instance of {@link ObtenerProducto }
     * 
     */
    public ObtenerProducto createObtenerProducto() {
        return new ObtenerProducto();
    }

    /**
     * Create an instance of {@link ListarProductos }
     * 
     */
    public ListarProductos createListarProductos() {
        return new ListarProductos();
    }

    /**
     * Create an instance of {@link DtFecha }
     * 
     */
    public DtFecha createDtFecha() {
        return new DtFecha();
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
     * Create an instance of {@link Producto }
     * 
     */
    public Producto createProducto() {
        return new Producto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Saludar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "saludar")
    public JAXBElement<Saludar> createSaludar(Saludar value) {
        return new JAXBElement<Saludar>(_Saludar_QNAME, Saludar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProductoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "obtenerProductoResponse")
    public JAXBElement<ObtenerProductoResponse> createObtenerProductoResponse(ObtenerProductoResponse value) {
        return new JAXBElement<ObtenerProductoResponse>(_ObtenerProductoResponse_QNAME, ObtenerProductoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarProductosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "listarProductosResponse")
    public JAXBElement<ListarProductosResponse> createListarProductosResponse(ListarProductosResponse value) {
        return new JAXBElement<ListarProductosResponse>(_ListarProductosResponse_QNAME, ListarProductosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaludarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "saludarResponse")
    public JAXBElement<SaludarResponse> createSaludarResponse(SaludarResponse value) {
        return new JAXBElement<SaludarResponse>(_SaludarResponse_QNAME, SaludarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarProductos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "listarProductos")
    public JAXBElement<ListarProductos> createListarProductos(ListarProductos value) {
        return new JAXBElement<ListarProductos>(_ListarProductos_QNAME, ListarProductos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerProducto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.market.com/", name = "obtenerProducto")
    public JAXBElement<ObtenerProducto> createObtenerProducto(ObtenerProducto value) {
        return new JAXBElement<ObtenerProducto>(_ObtenerProducto_QNAME, ObtenerProducto.class, null, value);
    }

}
