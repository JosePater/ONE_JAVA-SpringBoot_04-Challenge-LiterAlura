package com.josepaternina.literalura.service;

public interface IConvierteDatos {
    // Tipo de datos genéricos
    <T> T obtenerDatos(String json, Class<T> clase);
}
