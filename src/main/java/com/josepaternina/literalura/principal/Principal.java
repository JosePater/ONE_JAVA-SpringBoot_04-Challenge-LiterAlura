package com.josepaternina.literalura.principal;

import com.josepaternina.literalura.service.ConsumoAPI;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/";

    public void mostrarMenu() {
        System.out.println("Iniciando proyecto!!!");
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println("Los resultados de la consulta: " + json);
    }
}
