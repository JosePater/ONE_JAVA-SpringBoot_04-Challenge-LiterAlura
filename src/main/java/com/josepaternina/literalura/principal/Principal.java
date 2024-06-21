package com.josepaternina.literalura.principal;

import com.josepaternina.literalura.model.Datos;
import com.josepaternina.literalura.model.DatosLibros;
import com.josepaternina.literalura.service.ConsumoAPI;
import com.josepaternina.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "http://gutendex.com/books/";
    Scanner teclado = new Scanner(System.in);

    public void mostrarMenu() {
        String menu = """
                \nMenú de opciones:
                1. Buscar libros por títulos
                0. Salir
                                
                Elija una opción: """;

        var opcion = -1;
        while (opcion != 0) {
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    this.buscarLibros();
                    break;
                case 0:
                    System.out.println("Aplicación finalizada!!!");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    // Buscar libros por título
    public void buscarLibros() {
        System.out.println("\nEscriba el título del libro:");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\nLibro encontrado");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado!");
        }

    }
}