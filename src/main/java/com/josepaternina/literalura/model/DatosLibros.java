package com.josepaternina.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Ignora las propiedades de la api que no se han especificado aquí
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        // @JsonAlias(): Solo permite leer datos | @JsonProperty: leer y escribir (para una db)
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double cantidadDeDescarga
) {
}