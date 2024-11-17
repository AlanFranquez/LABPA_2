
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para proveedor complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="proveedor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services/}usuario">
 *       &lt;sequence>
 *         &lt;element name="compania" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "proveedor", propOrder = {
    "compania",
    "link"
})
public class Proveedor
    extends Usuario
{

    protected String compania;
    protected String link;

    /**
     * Obtiene el valor de la propiedad compania.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompania() {
        return compania;
    }

    /**
     * Define el valor de la propiedad compania.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompania(String value) {
        this.compania = value;
    }

    /**
     * Obtiene el valor de la propiedad link.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Define el valor de la propiedad link.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

}
