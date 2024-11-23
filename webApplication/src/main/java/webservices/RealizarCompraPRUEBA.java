
package webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para realizarCompraPRUEBA complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="realizarCompraPRUEBA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://services/}item" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "realizarCompraPRUEBA", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3"
})
public class RealizarCompraPRUEBA {

    protected List<Item> arg0;
    protected float arg1;
    protected String arg2;
    protected String arg3;

    /**
     * Gets the value of the arg0 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arg0 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArg0().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Item }
     * 
     * 
     */
    public List<Item> getArg0() {
        if (arg0 == null) {
            arg0 = new ArrayList<Item>();
        }
        return this.arg0;
    }

    /**
     * Obtiene el valor de la propiedad arg1.
     * 
     */
    public float getArg1() {
        return arg1;
    }

    /**
     * Define el valor de la propiedad arg1.
     * 
     */
    public void setArg1(float value) {
        this.arg1 = value;
    }

    /**
     * Obtiene el valor de la propiedad arg2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Define el valor de la propiedad arg2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg2(String value) {
        this.arg2 = value;
    }

    /**
     * Obtiene el valor de la propiedad arg3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg3() {
        return arg3;
    }

    /**
     * Define el valor de la propiedad arg3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg3(String value) {
        this.arg3 = value;
    }

}
