package services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Define que esta clase puede ser la raíz de un documento XML
public class ReclamoDTO {
    private String texto;
    private String autor;
    private String nombreProducto;
    String fecha;

    public ReclamoDTO() {}

    public ReclamoDTO(String texto, String autor, String nombreProducto, String fecha) {
        this.texto = texto;
        this.autor = autor;
        this.nombreProducto = nombreProducto;
        this.fecha = fecha;
    }

    @XmlElement // Anota el atributo para incluirlo en el XML
    public String getTexto() {
        return this.texto;
    }
    
    @XmlElement
    public String getAutor() {
        return this.autor;
    }
    
    @XmlElement 
    public String getFecha() {
        return this.texto;
    }
    
    @XmlElement 
    public String getNombreProducto() {
        return this.nombreProducto;
    }
    
    

    
}
