
package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para comentario complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad autor.
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
     * Define el valor de la propiedad autor.
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
     * Obtiene el valor de la propiedad comentarioPadre.
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
     * Define el valor de la propiedad comentarioPadre.
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
     * Obtiene el valor de la propiedad fecha.
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
     * Define el valor de la propiedad fecha.
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
     * Obtiene el valor de la propiedad numero.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Define el valor de la propiedad numero.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Obtiene el valor de la propiedad texto.
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
     * Define el valor de la propiedad texto.
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
