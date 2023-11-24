package com.example.memory_m.controlador;

public class utility {
    public static final String TABLA_REMEDIO = "medicamentos";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_DESCRIPCION = "descripcion";

    // Corregido aquí, añadí espacios y la coma faltante
    public static final String CREAR_TABLA_REMEDIO = "CREATE TABLE " +
            TABLA_REMEDIO + " (" +
            CAMPO_TITULO + " TEXT, " +
            CAMPO_DESCRIPCION + " TEXT)";
}