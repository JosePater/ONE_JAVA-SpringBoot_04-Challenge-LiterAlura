package com.josepaternina.literalura.principal;

import com.josepaternina.literalura.model.Datos;
import com.josepaternina.literalura.service.ConsumoAPI;
import com.josepaternina.literalura.service.ConvierteDatos;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "http://gutendex.com/books/";

    public void mostrarMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println("Datos sin convertir: " + json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println("\n Datos convertidos: " + datos);
    }
}
