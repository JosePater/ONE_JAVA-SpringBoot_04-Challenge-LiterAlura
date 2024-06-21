package com.josepaternina.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignora las propiedades de la api que no se han especificado aquí
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        // @JsonAlias(): Solo permite leer datos | @JsonProperty: leer y escribir (para una db)
        @JsonAlias("id") Integer id,
        @JsonAlias("tile") String titulo,
        @JsonAlias("subjects") String tema,
        @JsonAlias("authors") String autores,
        @JsonAlias("languages") String idiomas,
        @JsonAlias("download_count") Integer cantidadDeDescarga
) {
}