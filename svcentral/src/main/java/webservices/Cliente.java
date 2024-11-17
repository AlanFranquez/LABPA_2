
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

}
