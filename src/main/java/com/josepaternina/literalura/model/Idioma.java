package com.josepaternina.literalura.model;

public enum Idioma {
    en("[en]", "Ingles"),
    es("[es]", "Español"),
    fr("[fr]", "Frances"),
    pt("[pt]", "Portugués");

    private String idiomaGutendex;
    private String idiomaEspanol;

    // Idioma Gutendex
    Idioma(String idiomaGutendex, String idiomaEspanol) {
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspanol = idiomaEspanol;
    }

    // Asocia el idioma abreviado
    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    // Idioma español
    public static Idioma fromEspanol(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + text);
    }

    public String getIdiomaGutendex() {
        return idiomaGutendex;
    }

    public String getIdiomaEspanol() {
        return idiomaEspanol;
    }
}