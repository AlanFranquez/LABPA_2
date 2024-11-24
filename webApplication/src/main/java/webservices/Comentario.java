
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for comentario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comentario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autor" type="{http://services/}cliente" minOccurs="0"/>
 *         &lt;element name="comentarioPadre" type="{http://services/}comentario" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://services/}localDateTime" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="texto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comentario", propOrder = {
    "autor",
    "comentarioPadre",
    "fecha",
    "numero",
    "texto"
})
public class Comentario {

    protected Cliente autor;
    protected Comentario comentarioPadre;
    protected LocalDateTime fecha;
    protected int numero;
    protected String texto;

    /**
     * Gets the value of the autor property.
     * 
     * @return
     *     possible object is
     *     {@link Cliente }
     *     
     */
    public Cliente getAutor() {
        return autor;
    }

    /**
     * Sets the value of the autor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cliente }
     *     
     */
    public void setAutor(Cliente value) {
        this.autor = value;
    }

    /**
     * Gets the value of the comentarioPadre property.
     * 
     * @return
     *     possible object is
     *     {@link Comentario }
     *     
     */
    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    /**
     * Sets the value of the comentarioPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comentario }
     *     
     */
    public void setComentarioPadre(Comentario value) {
        this.comentarioPadre = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDateTime }
     *     
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDateTime }
     *     
     */
    public void setFecha(LocalDateTime value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the texto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets the value of the texto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTexto(String value) {
        this.texto = value;
    }

}
