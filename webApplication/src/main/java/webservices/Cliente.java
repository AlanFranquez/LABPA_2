
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
    "carrito"
})
public class Cliente
    extends Usuario
{

    protected Carrito carrito;

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

}
