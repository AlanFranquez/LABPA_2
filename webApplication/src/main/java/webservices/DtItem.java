
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cant" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="producto" type="{http://services/}producto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtItem", propOrder = {
    "cant",
    "producto"
})
public class DtItem {

    protected int cant;
    protected Producto producto;

    /**
     * Gets the value of the cant property.
     * 
     */
    public int getCant() {
        return cant;
    }

    /**
     * Sets the value of the cant property.
     * 
     */
    public void setCant(int value) {
        this.cant = value;
    }

    /**
     * Gets the value of the producto property.
     * 
     * @return
     *     possible object is
     *     {@link Producto }
     *     
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Sets the value of the producto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Producto }
     *     
     */
    public void setProducto(Producto value) {
        this.producto = value;
    }

}
