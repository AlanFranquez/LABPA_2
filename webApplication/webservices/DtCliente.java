
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtCliente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtCliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notificaciones" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tokenDesactivacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCliente", propOrder = {
    "correo",
    "notificaciones",
    "tokenDesactivacion"
})
public class DtCliente {

    protected String correo;
    protected boolean notificaciones;
    protected String tokenDesactivacion;

    /**
     * Gets the value of the correo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Sets the value of the correo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreo(String value) {
        this.correo = value;
    }

    /**
     * Gets the value of the notificaciones property.
     * 
     */
    public boolean isNotificaciones() {
        return notificaciones;
    }

    /**
     * Sets the value of the notificaciones property.
     * 
     */
    public void setNotificaciones(boolean value) {
        this.notificaciones = value;
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

}
