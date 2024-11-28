
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtCliente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad correo.
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
     * Define el valor de la propiedad correo.
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
     * Obtiene el valor de la propiedad notificaciones.
     * 
     */
    public boolean isNotificaciones() {
        return notificaciones;
    }

    /**
     * Define el valor de la propiedad notificaciones.
     * 
     */
    public void setNotificaciones(boolean value) {
        this.notificaciones = value;
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

}
