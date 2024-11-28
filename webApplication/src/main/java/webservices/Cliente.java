
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cliente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cliente">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services/}usuario">
 *       &lt;sequence>
 *         &lt;element name="carrito" type="{http://services/}carrito" minOccurs="0"/>
 *         &lt;element name="recibirNotificaciones" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recibirNotificacionesComentario" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recibirNotificacionesNuevoProducto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tokenComentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tokenDesactivacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tokenNuevoProducto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cliente", propOrder = {
    "carrito",
    "recibirNotificaciones",
    "recibirNotificacionesComentario",
    "recibirNotificacionesNuevoProducto",
    "tokenComentario",
    "tokenDesactivacion",
    "tokenNuevoProducto"
})
public class Cliente
    extends Usuario
{

    protected Carrito carrito;
    protected boolean recibirNotificaciones;
    protected boolean recibirNotificacionesComentario;
    protected boolean recibirNotificacionesNuevoProducto;
    protected String tokenComentario;
    protected String tokenDesactivacion;
    protected String tokenNuevoProducto;

    /**
     * Obtiene el valor de la propiedad carrito.
     * 
     * @return
     *     possible object is
     *     {@link Carrito }
     *     
     */
    public Carrito getCarrito() {
        return carrito;
    }

    /**
     * Define el valor de la propiedad carrito.
     * 
     * @param value
     *     allowed object is
     *     {@link Carrito }
     *     
     */
    public void setCarrito(Carrito value) {
        this.carrito = value;
    }

    /**
     * Obtiene el valor de la propiedad recibirNotificaciones.
     * 
     */
    public boolean isRecibirNotificaciones() {
        return recibirNotificaciones;
    }

    /**
     * Define el valor de la propiedad recibirNotificaciones.
     * 
     */
    public void setRecibirNotificaciones(boolean value) {
        this.recibirNotificaciones = value;
    }

    /**
     * Obtiene el valor de la propiedad recibirNotificacionesComentario.
     * 
     */
    public boolean isRecibirNotificacionesComentario() {
        return recibirNotificacionesComentario;
    }

    /**
     * Define el valor de la propiedad recibirNotificacionesComentario.
     * 
     */
    public void setRecibirNotificacionesComentario(boolean value) {
        this.recibirNotificacionesComentario = value;
    }

    /**
     * Obtiene el valor de la propiedad recibirNotificacionesNuevoProducto.
     * 
     */
    public boolean isRecibirNotificacionesNuevoProducto() {
        return recibirNotificacionesNuevoProducto;
    }

    /**
     * Define el valor de la propiedad recibirNotificacionesNuevoProducto.
     * 
     */
    public void setRecibirNotificacionesNuevoProducto(boolean value) {
        this.recibirNotificacionesNuevoProducto = value;
    }

    /**
     * Obtiene el valor de la propiedad tokenComentario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenComentario() {
        return tokenComentario;
    }

    /**
     * Define el valor de la propiedad tokenComentario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenComentario(String value) {
        this.tokenComentario = value;
    }

    /**
     * Obtiene el valor de la propiedad tokenDesactivacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenDesactivacion() {
        return tokenDesactivacion;
    }

    /**
     * Define el valor de la propiedad tokenDesactivacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenDesactivacion(String value) {
        this.tokenDesactivacion = value;
    }

    /**
     * Obtiene el valor de la propiedad tokenNuevoProducto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenNuevoProducto() {
        return tokenNuevoProducto;
    }

    /**
     * Define el valor de la propiedad tokenNuevoProducto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenNuevoProducto(String value) {
        this.tokenNuevoProducto = value;
    }

}
