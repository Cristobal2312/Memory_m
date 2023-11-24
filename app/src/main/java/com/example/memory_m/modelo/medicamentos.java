package com.example.memory_m.modelo;

public class medicamentos {


    private String titulo;
    private String descripcion;
    public medicamentos(){

    }

    public medicamentos( String titulo, String descripcion) {

        this.titulo = titulo;
        this.descripcion = descripcion;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "medicamentos{" +

                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
