
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cliente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the carrito property.
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
     * Sets the value of the carrito property.
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
     * Gets the value of the recibirNotificaciones property.
     * 
     */
    public boolean isRecibirNotificaciones() {
        return recibirNotificaciones;
    }

    /**
     * Sets the value of the recibirNotificaciones property.
     * 
     */
    public void setRecibirNotificaciones(boolean value) {
        this.recibirNotificaciones = value;
    }

    /**
     * Gets the value of the recibirNotificacionesComentario property.
     * 
     */
    public boolean isRecibirNotificacionesComentario() {
        return recibirNotificacionesComentario;
    }

    /**
     * Sets the value of the recibirNotificacionesComentario property.
     * 
     */
    public void setRecibirNotificacionesComentario(boolean value) {
        this.recibirNotificacionesComentario = value;
    }

    /**
     * Gets the value of the recibirNotificacionesNuevoProducto property.
     * 
     */
    public boolean isRecibirNotificacionesNuevoProducto() {
        return recibirNotificacionesNuevoProducto;
    }

    /**
     * Sets the value of the recibirNotificacionesNuevoProducto property.
     * 
     */
    public void setRecibirNotificacionesNuevoProducto(boolean value) {
        this.recibirNotificacionesNuevoProducto = value;
    }

    /**
     * Gets the value of the tokenComentario property.
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
     * Sets the value of the tokenComentario property.
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
     * Gets the value of the tokenDesactivacion property.
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
     * Sets the value of the tokenDesactivacion property.
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
     * Gets the value of the tokenNuevoProducto property.
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
     * Sets the value of the tokenNuevoProducto property.
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
