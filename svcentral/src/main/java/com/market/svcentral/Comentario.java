package com.market.svcentral;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comentario {
    @Id
    private int numero;
    
    private String texto;

    @ManyToOne(cascade = CascadeType.MERGE) 
    @JoinColumn(name = "cliente_nick")
    private Cliente autor;

    private LocalDateTime fecha;

    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL)
    private List<Comentario> respuestas;

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    public Comentario() {
        this.respuestas = new ArrayList<>();
    }

    public Comentario(int numero, String texto, Cliente autor, LocalDateTime fecha) {
        this.numero = numero;
        this.texto = texto;
        this.autor = autor;
        this.fecha = fecha;
        this.respuestas = new ArrayList<>();
    }

    public void agregarRespuesta(Comentario respuesta) {
        respuesta.setComentarioPadre(this);
        this.respuestas.add(respuesta);
    }

    public List<Comentario> getRespuestas() {
        return this.respuestas;
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Cliente getAutor() {
        return autor;
    }

    public void setAutor(Cliente autor) {
        this.autor = autor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }
}
