package com.josepaternina.literalura.repository;

import com.josepaternina.literalura.model.Idioma;
import com.josepaternina.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByIdiomas(Idioma idioma);

    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l ORDER BY l.numero_descargas DESC LIMIT 10")
    List<Libro> top10LibrosMasDescargados();
}
