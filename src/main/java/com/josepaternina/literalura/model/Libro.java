package com.josepaternina.literalura.model;

import jakarta.persistence.*;

@Entity // Entidad
public class Libro {
    @Id // Identificador
    private Integer id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idiomas;
    private Integer numero_descargas;

    // Constructor
    public Libro() {
    }

    public Libro(DatosLibros datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = Idioma.fromString(datosLibro.idiomas().toString().split(",")[0].trim());
        this.numero_descargas = datosLibro.cantidadDeDescarga();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumero_descargas() {
        return numero_descargas;
    }

    public void setNumero_descargas(Integer numero_descargas) {
        this.numero_descargas = numero_descargas;
    }
}

