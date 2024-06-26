package com.josepaternina.literalura.principal;

import com.josepaternina.literalura.model.*;
import com.josepaternina.literalura.repository.AutorRepository;
import com.josepaternina.literalura.repository.LibroRepository;
import com.josepaternina.literalura.service.ConsumoAPI;
import com.josepaternina.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "http://gutendex.com/books/";
    private LibroRepository repositoryLibro;
    private AutorRepository repositoryAutor;
    private List<Autor> autores;
    private List<Libro> libros;
    Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository repositoryLibro, AutorRepository repositoryAutor) {
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \nMENÚ DE OPCIONES:
                    1. Buscar libros por su título
                    2. Mostrar todos los libros registrados
                    3. Mostrar todos los autores registrados
                    4. Autores vivos en cierto año
                    5. Buscar libros según su idioma
                    6. Top 10 de los libros más descargados
                                        
                    0 - Salir
                    """;
            System.out.println(menu);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato inválido, digite un número del menú!");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    autoresVivosPorAnio();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                case 6:
                    top10LibrosMasDescargados();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación");
                    break;
                default:
                    System.out.printf("Opción inválida\n");
            }
        }
    }

    // Verificar si hay datos en la base de datos
    private boolean idDateDB() {
        try {
            libros = repositoryLibro.findAll();
            libros.stream()
                    .forEach(System.out::println);
            return true;
        } catch (NullPointerException e) {
            System.out.println("\nNo hay registros en la base de datos!");
            return false;
        }
    }

    private DatosBusqueda getBusqueda() {
        System.out.println("Escribe el nombre del libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        DatosBusqueda datos = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datos;

    }

    private void buscarLibro() {
        DatosBusqueda datosBusqueda = getBusqueda();
        if (datosBusqueda != null && !datosBusqueda.resultado().isEmpty()) {
            DatosLibros primerLibro = datosBusqueda.resultado().get(0);


            Libro libro = new Libro(primerLibro);
            System.out.println("----- Libro -----");
            System.out.println(libro);
            System.out.println("-----------------");

            Optional<Libro> libroExiste = repositoryLibro.findByTitulo(libro.getTitulo());
            if (libroExiste.isPresent()) {
                System.out.println("\nEl libro ya esta registrado\n");
            } else {

                if (!primerLibro.autor().isEmpty()) {
                    DatosAutor autor = primerLibro.autor().get(0);
                    Autor autor1 = new Autor(autor);
                    Optional<Autor> autorOptional = repositoryAutor.findByNombre(autor1.getNombre());

                    if (autorOptional.isPresent()) {
                        Autor autorExiste = autorOptional.get();
                        libro.setAutor(autorExiste);
                        repositoryLibro.save(libro);
                    } else {
                        Autor autorNuevo = repositoryAutor.save(autor1);
                        libro.setAutor(autorNuevo);
                        repositoryLibro.save(libro);
                    }

                    Integer numeroDescargas = libro.getNumero_descargas() != null ? libro.getNumero_descargas() : 0;
                    System.out.println("---------- Libro ----------");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%nNumero de Descargas: %s%n",
                            libro.getTitulo(), autor1.getNombre(), libro.getIdiomas(), libro.getNumero_descargas());
                    System.out.println("---------------------------\n");
                } else {
                    System.out.println("Sin autor");
                }
            }
        } else {
            System.out.println("libro no encontrado");
        }
    }

    private void mostrarLibros() {
        if (!idDateDB()) return;
        libros = repositoryLibro.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutores() {
        if (!idDateDB()) return;
        autores = repositoryAutor.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void autoresVivosPorAnio() {
        if (!idDateDB()) return;
        System.out.println("Ingresa el año vivo de autor(es) que desea buscar: ");
        var anio = teclado.nextInt();
        autores = repositoryAutor.listaAutoresVivosPorAnio(anio);
        autores.stream()
                .forEach(System.out::println);
    }

    private List<Libro> datosBusquedaLenguaje(String idioma) {
        var dato = Idioma.fromString(idioma);
        System.out.println("Lenguaje buscado: " + dato);

        List<Libro> libroPorIdioma = repositoryLibro.findByIdiomas(dato);
        return libroPorIdioma;
    }

    private void buscarLibroPorIdioma() {
        if (!idDateDB()) return;
        System.out.println("Selecciona el lenguaje/idioma que deseas buscar: ");

        var opcion = -1;
        while (opcion != 0) {
            var opciones = """
                    1. en - Ingles
                    2. es - Español
                    3. fr - Francés
                    4. pt - Portugués
                                        
                    0. Volver a Las opciones anteriores
                    """;
            System.out.println(opciones);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato inválido, ingrese un número que esté disponible en el menú");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Libro> librosEnIngles = datosBusquedaLenguaje("[en]");
                    librosEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Libro> librosEnEspanol = datosBusquedaLenguaje("[es]");
                    librosEnEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Libro> librosEnFrances = datosBusquedaLenguaje("[fr]");
                    librosEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Libro> librosEnPortugues = datosBusquedaLenguaje("[pt]");
                    librosEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ningún idioma seleccionado");
            }
        }
    }

    private void top10LibrosMasDescargados() {
        if (!idDateDB()) return;
        List<Libro> topLibros = repositoryLibro.top10LibrosMasDescargados();
        topLibros.forEach(System.out::println);
    }

}